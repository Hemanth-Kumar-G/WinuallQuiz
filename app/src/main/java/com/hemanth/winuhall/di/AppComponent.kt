package com.hemanth.winuhall.di

import com.hemanth.winuhall.AppController
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        AppUtilModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<AppController> {

    override fun inject(instance: AppController?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(appController: AppController): Builder

        fun build(): AppComponent
    }
}