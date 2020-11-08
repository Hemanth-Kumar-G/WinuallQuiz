package com.hemanth.winuhall.ui.quizTest

import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.di.scope.ActivityScoped
import dagger.Module
import dagger.Provides

@Module
class QuizTestUtil {

    @Provides
    @ActivityScoped
    fun provideList(): ArrayList<QuizQuestionResponse.Data> = ArrayList()

    @Provides
    @ActivityScoped
    fun provideAdapter() = QuizOptionAdapter()
}