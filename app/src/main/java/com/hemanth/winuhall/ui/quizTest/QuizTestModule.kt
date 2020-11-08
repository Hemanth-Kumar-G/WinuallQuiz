package com.hemanth.winuhall.ui.quizTest

import com.hemanth.winuhall.di.scope.FragmentScoped
import com.hemanth.winuhall.ui.quizTest.quizStartScreen.QuizStartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class QuizTestModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun quizStartFragment(): QuizStartFragment
}