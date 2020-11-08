package com.hemanth.winuhall.ui.home.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemanth.winuhall.R
import com.hemanth.winuhall.data.model.Upcoming
import com.hemanth.winuhall.databinding.QuizBranchFragmentBinding
import com.hemanth.winuhall.ui.home.HomeUtil
import com.hemanth.winuhall.ui.home.model.QuizBranchAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named


/**
 *<h1>UpcomingFragment</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class UpcomingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Named(HomeUtil.UP_COMING)
    @Inject
    lateinit var adapter: QuizBranchAdapter

    @Inject
    lateinit var upComingList: ArrayList<Upcoming>

    private lateinit var mBinding: QuizBranchFragmentBinding

    private val viewModel: UpcomingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(UpcomingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.quiz_branch_fragment, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    private fun initRv() {
        mBinding.rv.adapter = adapter
    }

    fun notifyApiData() {
        mBinding.lottieLoading.cancelAnimation()
        mBinding.lottieLoading.visibility = View.GONE
        adapter.notifyDataSetChanged()
        mBinding.grpEmpty.visibility = if (upComingList.isEmpty()) View.VISIBLE else View.GONE
    }

}