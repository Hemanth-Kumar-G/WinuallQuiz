package com.hemanth.winuhall.ui.home.ongoing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemanth.winuhall.R
import com.hemanth.winuhall.data.model.Ongoing
import com.hemanth.winuhall.databinding.QuizBranchFragmentBinding
import com.hemanth.winuhall.ui.home.HomeUtil
import com.hemanth.winuhall.ui.home.model.QuizBranchAdapter
import com.hemanth.winuhall.ui.quizTest.QuizTestActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import javax.inject.Named

/**
 *<h1>OngoingFragment</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class OngoingFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var ongoingList: ArrayList<Ongoing>

    @Named(HomeUtil.ON_GOING)
    @Inject
    lateinit var adapter: QuizBranchAdapter

    private lateinit var mBinding: QuizBranchFragmentBinding

    private val viewModel: OngoingViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OngoingViewModel::class.java)
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
        adapter.onSelectionChangeListener = {
            startActivity(Intent(requireActivity(), QuizTestActivity::class.java))
        }
    }

    fun notifyApiData() {
        mBinding.lottieLoading.cancelAnimation()
        mBinding.lottieLoading.visibility = View.GONE
        adapter.notifyDataSetChanged()
        mBinding.grpEmpty.visibility = if (ongoingList.isEmpty()) View.VISIBLE else View.GONE
    }
}