package com.hemanth.winuhall.ui.quizTest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.databinding.QuizOptionItemBinding

/**
 *<h1>QuizOptionAdapter</h1>
 * <p>this is adapter class ,used for showing the multiple option of questions</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class QuizOptionAdapter : RecyclerView.Adapter<QuizOptionAdapter.QuizOptionViewHolder>() {

    var quizList: ArrayList<QuizQuestionResponse.Data.Option>? = null
    var isMultiple: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizOptionViewHolder {
        val binding = QuizOptionItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return QuizOptionViewHolder(binding)
    }

    override fun getItemCount(): Int = quizList?.size ?: 0

    override fun onBindViewHolder(holder: QuizOptionViewHolder, position: Int) {
        holder.binding.data = quizList?.get(position)
        holder.binding.viewClick.setOnClickListener {
            if (isMultiple) {
                val data = quizList?.get(position)
                data?.let { it.isSelected = !it.isSelected }
                holder.binding.data = data
            } else {
                handleSCQ(position)
                holder.binding.data = quizList?.get(position)
            }
            notifyDataSetChanged()
        }
    }

    /**
     * <h2>handleSCQ</h2>
     * this method handle single selection
     */
    private fun handleSCQ(position: Int) {
        quizList?.forEachIndexed { i: Int, option: QuizQuestionResponse.Data.Option ->
            option.isSelected = i == position
        }
    }

    class QuizOptionViewHolder(val binding: QuizOptionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}