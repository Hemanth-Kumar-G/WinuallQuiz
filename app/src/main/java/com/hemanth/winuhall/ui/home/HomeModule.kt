package com.hemanth.winuhall.ui.home

import android.app.Activity
import com.hemanth.winuhall.di.scope.ActivityScoped
import com.hemanth.winuhall.di.scope.FragmentScoped
import com.hemanth.winuhall.ui.home.completed.CompletedFragment
import com.hemanth.winuhall.ui.home.ongoing.OngoingFragment
import com.hemanth.winuhall.ui.home.upcoming.UpcomingFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun provideCompletedFragment(): CompletedFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun provideOngoingFragment(): OngoingFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun provideUpcomingFragment(): UpcomingFragment

    @ActivityScoped
    @Binds
    abstract fun provideActivity(activity: HomeActivity): Activity
}