package com.hemanth.winuhall.di


import com.hemanth.winuhall.di.scope.ActivityScoped
import com.hemanth.winuhall.ui.home.HomeActivity
import com.hemanth.winuhall.ui.home.HomeModule
import com.hemanth.winuhall.ui.home.HomeUtil
import com.hemanth.winuhall.ui.quizTest.QuizTestActivity
import com.hemanth.winuhall.ui.quizTest.QuizTestModule
import com.hemanth.winuhall.ui.quizTest.QuizTestUtil
import com.hemanth.winuhall.ui.result.ResultActivity
import com.hemanth.winuhall.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [ViewModelModule::class]
)
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class, HomeUtil::class])
    abstract fun homeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [QuizTestModule::class, QuizTestUtil::class])
    abstract fun quizTestActivity(): QuizTestActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun resultActivity(): ResultActivity

}