package com.hemanth.winuhall.di

import android.content.Context
import com.hemanth.winuhall.AppController
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindApplication(appController: AppController): Context
}