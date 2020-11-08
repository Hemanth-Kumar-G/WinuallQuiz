package com.hemanth.winuhall.ui.home.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemanth.winuhall.R
import com.hemanth.winuhall.data.model.Completed
import com.hemanth.winuhall.databinding.QuizBranchFragmentBinding
import com.hemanth.winuhall.ui.home.HomeUtil
import com.hemanth.winuhall.ui.home.model.QuizBranchAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

/**
 *<h1>CompletedFragment</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class CompletedFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var completedList: ArrayList<Completed>

    @Named(HomeUtil.COMPLETED)
    @Inject
    lateinit var adapter: QuizBranchAdapter

    private lateinit var mBinding: QuizBranchFragmentBinding

    private val viewModel: CompletedViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CompletedViewModel::class.java)
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
        mBinding.grpEmpty.visibility = if (completedList.isEmpty()) View.VISIBLE else View.GONE
    }
}