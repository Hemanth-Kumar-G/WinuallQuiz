package com.hemanth.winuhall.data.repository

import com.hemanth.winuhall.data.model.QuizBatchesResponse
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import io.reactivex.Single
import retrofit2.Response


interface HomeRepository {

    fun getQuizBatchDetails(): Single<Response<QuizBatchesResponse>>

    fun getQuizQuestion(): Single<Response<QuizQuestionResponse>>
}