package com.hemanth.winuhall.ui.quizTest

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.hemanth.winuhall.R
import com.hemanth.winuhall.common.ConnectivityReceiver
import com.hemanth.winuhall.common.Constants
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.data.model.ResultResponse
import com.hemanth.winuhall.databinding.ActivityQuizTestBinding
import com.hemanth.winuhall.ui.quizTest.quizStartScreen.QuizStartFragment
import com.hemanth.winuhall.ui.result.ResultActivity
import com.hemanth.winuhall.utils.Util
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


/**
 *<h1>QuizTestActivity</h1>
 * <p>this activity for test</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class QuizTestActivity : DaggerAppCompatActivity(), View.OnClickListener,
    ConnectivityReceiver.ConnectivityReceiverListener {

    companion object {
        const val COUNT_DOWN_INTERVAL: Long = 1000
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var quizStartFragment: QuizStartFragment

    @Inject
    lateinit var quizList: ArrayList<QuizQuestionResponse.Data>

    @Inject
    lateinit var quizOptionAdapter: QuizOptionAdapter

    @Inject
    lateinit var adapter: QuizOptionAdapter

    private val viewModel: QuizTestViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(QuizTestViewModel::class.java)
    }

    private lateinit var timer: CountDownTimer
    private lateinit var mBinding: ActivityQuizTestBinding
    private lateinit var data: QuizQuestionResponse.Data
    private var index = 0
    private var isConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz_test)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mBinding.viewModel = viewModel
        viewModel.quizList = quizList
        initRv()
        initOnClick()
        viewModel.getQuizQuestion()
        setupObserver()
        setupResultObserver()
        startQuizLister()
        initNetwork()
    }

    /**
     * <h2>initNetwork</h2>
     * registering the broadcast receiver for network observering
     */
    private fun initNetwork() {
        registerReceiver(
            ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun setupResultObserver() {
        viewModel.eventResult.observe(this, Observer {
            launchResultActivity(it.getContent())
        })
    }


    private fun initOnClick() {
        mBinding.btnNext.setOnClickListener(this)
        mBinding.btnPrevious.setOnClickListener(this)
        mBinding.tvQuizFinish.setOnClickListener(this)
    }

    private fun startQuizLister() {
        quizStartFragment.onStartLister = {
            if (it) timer.start()
        }
    }

    private fun initRv() {
        mBinding.rv.adapter = adapter
    }

    /**
     * <h2>setupObserver</h2>
     * this method is for observing the data from api
     */
    private fun setupObserver() {
        viewModel.eventQuizBatch.observe(this, Observer {
            when (it.getContentIfNotPending()?.first) {

                Constants.SUCCESS -> {
                    val quizQuestionResponse =
                        (it.getContent().second as QuizQuestionResponse?)!!
                    addData(quizQuestionResponse)
                    openWelcomeScreen()
                    GlobalScope.launch {
                        kotlinx.coroutines.delay(500)
                        viewModel.setLoading(false)
                    }
                    handleQuestions(index)
                }

                Constants.ERROR -> showError(it.getContent().second.toString())
                Constants.NO_INTERNET -> noInternetError()
            }
        })
    }

    private fun noInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.no_internet_error))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> viewModel.getQuizQuestion() }
            .setOnCancelListener { viewModel.getQuizQuestion() }
            .show()
    }

    private fun showError(error: String) {
        val snackBar: Snackbar = Snackbar.make(mBinding.root, error, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(this, R.color.snackbar_error_background_color)
        )
        snackBar.show()
    }

    private fun openWelcomeScreen() {
        quizStartFragment.show(supportFragmentManager, quizStartFragment::class.java.simpleName)
    }

    private fun addData(quizQuestionResponse: QuizQuestionResponse) {
        quizStartFragment.showData(quizQuestionResponse.quiz)
        setUpTimer(quizQuestionResponse.quiz.duration.toLong())

    }

    private fun setUpTimer(duration: Long?) {
        if (duration != null) {
            timer = object :
                CountDownTimer(
                    duration * Constants.SECONDS * Constants.MILLI_SECONDS, COUNT_DOWN_INTERVAL
                ) {
                override fun onTick(millisUntilFinished: Long) {
                    mBinding.tvQuizTimer.text = Util.timeFormatString(millisUntilFinished)
                }

                override fun onFinish() {
                    mBinding.tvQuizTimer.text = getString(R.string.finished)
                }

            }
        }
    }

    private fun handleQuestions(index: Int) {
        if (index < quizList.size) {
            data = quizList[index]
            mBinding.data = data
            viewModel.index.set(index + 1)
            adapter.quizList = data.options as ArrayList<QuizQuestionResponse.Data.Option>
            adapter.isMultiple = data.isMultiple
            adapter.notifyDataSetChanged()
        } else
            onFinish()

        mBinding.btnNext.text =
            if (index < quizList.size - 1) getString(R.string.next) else getString(R.string.finish)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnNext -> handleQuestions(index = ++index)
            R.id.btnPrevious -> handleQuestions(index = --index)
            R.id.tvQuizFinish -> onFinish()
        }
    }

    private fun onFinish() {
        if (isConnected) showFinishDialog() else showInternetError()
    }

    private fun showInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.internet_disconnected))
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun showFinishDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.finish_quiz_msg))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> viewModel.validateAnswer() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun launchResultActivity(result: ResultResponse) {
        pushDataToFCM(result)
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.QUIZ_RESULT, result)
        startActivity(intent)
    }

    /**
     * <h2>pushDataToFCM</h2>
     * saving the result in firebase realDB
     * as of now user has hardcoded
     */
    private fun pushDataToFCM(result: ResultResponse) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(Constants.USERS)
        mDatabase.child(Constants.HARD_CODED_USER).setValue(result)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        viewModel.internetAvailable.set(isConnected)
    }

    override fun onBackPressed() {
        onFinish()
    }
}