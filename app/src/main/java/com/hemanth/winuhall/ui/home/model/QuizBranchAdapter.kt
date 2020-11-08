package com.hemanth.winuhall.ui.home.model

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hemanth.winuhall.R
import com.hemanth.winuhall.data.model.Completed
import com.hemanth.winuhall.data.model.Ongoing
import com.hemanth.winuhall.data.model.Upcoming
import com.hemanth.winuhall.databinding.QuizBranchItemBinding
import com.hemanth.winuhall.ui.home.HomeUtil
import com.hemanth.winuhall.utils.Util

/**
 *<h1>QuizBranchAdapter</h1>
 * this adapter is same for all upcoming,ongoing,completed
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class QuizBranchAdapter(
    private val ongoingList: ArrayList<Ongoing>,
    private val upcoming: ArrayList<Upcoming>,
    private val completed: ArrayList<Completed>,
    private val type: String
) : RecyclerView.Adapter<QuizBranchAdapter.QuizBranchViewHolder>() {

    private lateinit var context: Context
    var onSelectionChangeListener: ((position: Int) -> Unit)? = null

    class QuizBranchViewHolder(val binding: QuizBranchItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizBranchViewHolder {
        context = parent.context
        val binding =
            QuizBranchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizBranchViewHolder(binding)
    }

    override fun getItemCount(): Int = when (type) {
        HomeUtil.ON_GOING -> ongoingList.size
        HomeUtil.UP_COMING -> upcoming.size
        HomeUtil.COMPLETED -> completed.size
        else -> 0
    }

    override fun onBindViewHolder(holder: QuizBranchViewHolder, position: Int) {
        when (type) {
            HomeUtil.ON_GOING -> handleOngoing(holder, position)
            HomeUtil.UP_COMING -> handleUpcoming(holder, position)
            HomeUtil.COMPLETED -> handleCompleted(holder, position)
        }
        holder.binding.ivStart.setOnClickListener {
            onSelectionChangeListener?.invoke(position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleCompleted(holder: QuizBranchAdapter.QuizBranchViewHolder, position: Int) {
        val data = completed.getOrNull(position)?.quizDetails?.getOrNull(0)
        holder.binding.tvTitle.text = data?.name
        holder.binding.tvDuration.text = data?.duration.toString() + context.getString(R.string.min)
        holder.binding.tvTime.text =
            "${Util.convertISOToDateTime(completed.getOrNull(position)?.scheduleStart)} -" +
                    " ${Util.convertISOToDateTime(completed.getOrNull(position)?.scheduleEnd)}"

    }

    @SuppressLint("SetTextI18n")
    private fun handleUpcoming(holder: QuizBranchAdapter.QuizBranchViewHolder, position: Int) {
        val data = upcoming.getOrNull(position)?.quizDetails?.getOrNull(0)
        holder.binding.tvTitle.text = data?.name
        holder.binding.tvDuration.text = data?.duration.toString() + context.getString(R.string.min)
        holder.binding.tvTime.text =
            "${Util.convertISOToDateTime(upcoming.getOrNull(position)?.scheduleStart)} -" +
                    " ${Util.convertISOToDateTime(upcoming.getOrNull(position)?.scheduleEnd)}"

    }


    @SuppressLint("SetTextI18n")
    private fun handleOngoing(holder: QuizBranchAdapter.QuizBranchViewHolder, position: Int) {
        val data = ongoingList.getOrNull(position)?.quizDetails?.getOrNull(0)
        holder.binding.tvTitle.text = data?.name
        holder.binding.tvDuration.text = data?.duration.toString() + context.getString(R.string.min)
        holder.binding.tvTime.text =
            "${Util.convertISOToDateTime(ongoingList.getOrNull(position)?.scheduleStart)} -" +
                    " ${Util.convertISOToDateTime(ongoingList.getOrNull(position)?.scheduleEnd)}"

    }
}