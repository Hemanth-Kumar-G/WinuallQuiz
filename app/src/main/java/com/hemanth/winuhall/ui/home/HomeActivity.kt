package com.hemanth.winuhall.ui.home

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.hemanth.winuhall.R
import com.hemanth.winuhall.common.Constants
import com.hemanth.winuhall.data.model.Completed
import com.hemanth.winuhall.data.model.Ongoing
import com.hemanth.winuhall.data.model.Upcoming
import com.hemanth.winuhall.databinding.ActivityHomeBinding
import com.hemanth.winuhall.ui.home.completed.CompletedFragment
import com.hemanth.winuhall.ui.home.ongoing.OngoingFragment
import com.hemanth.winuhall.ui.home.upcoming.UpcomingFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 *<h1>HomeActivity</h1>
 * <p>this activity is for consist of upcoming,ongoing,completed fragments</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var ongoingList: ArrayList<Ongoing>

    @Inject
    lateinit var upcomingList: ArrayList<Upcoming>

    @Inject
    lateinit var completedList: ArrayList<Completed>

    @Inject
    lateinit var upcomingFragment: UpcomingFragment

    @Inject
    lateinit var ongoingFragment: OngoingFragment

    @Inject
    lateinit var completedFragment: CompletedFragment

    @Inject
    lateinit var fragmentAdapter: HomeFragmentAdapter

    private lateinit var mBinding: ActivityHomeBinding
    private val mTabTile: Array<String> by lazy { resources.getStringArray(R.array.home_tab_titles) }
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initUi()
        initList()
        viewModel.getQuizBatchDetails()
        setupObserver()
    }

    private fun initList() {
        viewModel.completedList = completedList
        viewModel.ongoingList = ongoingList
        viewModel.upcomingList = upcomingList
    }

    private fun initUi() {
        mBinding.viewPager2Home.adapter = fragmentAdapter
        mBinding.viewPager2Home.offscreenPageLimit = 3
        TabLayoutMediator(mBinding.tabLayoutHome, mBinding.viewPager2Home) { tab, position ->
            tab.text = mTabTile[position]
        }.attach()
    }

    /**
     * <h2>setupObserver</h2>
     * this method is for observing the response from api
     */
    private fun setupObserver() {
        viewModel.eventQuizBatch.observe(this, Observer {
            when (it.getContentIfNotPending()?.first) {
                Constants.SUCCESS -> updateFragment()
                Constants.ERROR -> showError(it.getContent().second.toString())
                Constants.NO_INTERNET -> showInternetError()
            }
        })
    }

    private fun showInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.no_internet_error))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> viewModel.getQuizBatchDetails() }
            .setOnCancelListener { viewModel.getQuizBatchDetails() }
            .show()
    }

    /**
     * <h2>updateFragment</h2>
     * method is for notifying the fragment after api call
     */
    private fun updateFragment() {
        ongoingFragment.notifyApiData()
        upcomingFragment.notifyApiData()
        completedFragment.notifyApiData()
    }

    private fun showError(error: String) {
        val snackBar: Snackbar = Snackbar.make(mBinding.root, error, Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(this, R.color.snackbar_error_background_color)
        )
        snackBar.show()
    }


}