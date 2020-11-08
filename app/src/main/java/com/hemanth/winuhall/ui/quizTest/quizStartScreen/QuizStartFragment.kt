package com.hemanth.winuhall.ui.quizTest.quizStartScreen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hemanth.winuhall.R
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.databinding.FragmentQuizStartBinding
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject


/**
 *<h1>QuizStartFragment</h1>
 * <p>this activity for start screen for begining for test</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class QuizStartFragment @Inject constructor() : DaggerDialogFragment(), View.OnClickListener {

    lateinit var mBinding: FragmentQuizStartBinding
    lateinit var quiz: QuizQuestionResponse.Quiz
    var onStartLister: ((status: Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, height)
        dialog!!.window!!.setGravity(Gravity.FILL_HORIZONTAL or Gravity.CENTER_VERTICAL)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(true)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_start, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleStartButton()
        initClick()
        mBinding.quiz = quiz
    }

    private fun initClick() {
        mBinding.btnQuizStart.setOnClickListener(this)
        mBinding.ivQuizStartBack.setOnClickListener(this)
    }

    private fun handleStartButton() {
        if (mBinding.cbQuiz.isChecked) {
            onStartLister?.invoke(true)
            dismiss()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnQuizStart -> handleStartButton()
            R.id.ivQuizStartBack -> onBackPress()
        }
    }

    private fun onBackPress() {
        requireActivity().onBackPressed()
    }

    fun showData(quiz: QuizQuestionResponse.Quiz) {
        this.quiz = quiz
    }

}