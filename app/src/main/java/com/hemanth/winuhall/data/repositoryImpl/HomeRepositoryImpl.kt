package com.hemanth.winuhall.data.repositoryImpl

import com.hemanth.winuhall.data.RepoService
import com.hemanth.winuhall.data.model.QuizBatchesResponse
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.data.repository.HomeRepository
import io.reactivex.Single
import retrofit2.Response

class HomeRepositoryImpl(
    private val repoService: RepoService
) : HomeRepository {

    override fun getQuizBatchDetails(): Single<Response<QuizBatchesResponse>> =
        repoService.getQuizBatchDetails()

    override fun getQuizQuestion(): Single<Response<QuizQuestionResponse>> =
        repoService.getQuizQuestions()

}