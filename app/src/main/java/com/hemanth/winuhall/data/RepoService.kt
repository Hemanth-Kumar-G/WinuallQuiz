package com.hemanth.winuhall.data

import com.hemanth.winuhall.data.model.QuizBatchesResponse
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface RepoService {

    @GET("/b00b070fc2461a68383b")
    fun getQuizBatchDetails(): Single<Response<QuizBatchesResponse>>

    @GET("/c454bbb861f9ab1e9309")
    fun getQuizQuestions(): Single<Response<QuizQuestionResponse>>

}