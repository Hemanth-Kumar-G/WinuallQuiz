package com.hemanth.winuhall.di

import androidx.lifecycle.ViewModel
import com.hemanth.winuhall.di.scope.ViewModelKey
import com.hemanth.winuhall.ui.home.HomeViewModel
import com.hemanth.winuhall.ui.home.completed.CompletedViewModel
import com.hemanth.winuhall.ui.home.ongoing.OngoingViewModel
import com.hemanth.winuhall.ui.home.upcoming.UpcomingViewModel
import com.hemanth.winuhall.ui.quizTest.QuizTestViewModel
import com.hemanth.winuhall.ui.result.ResultViewModel
import com.hemanth.winuhall.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule : ViewModelFactoryModule() {

    /*
    * This method basically says
    * inject this object into a Map using the @IntoMap annotation,
    * with the  LoginViewModel.class as key,
    * and a Provider that will build a LoginViewModel
    * object.
    *
    * */

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CompletedViewModel::class)
    abstract fun completedViewModel(completedViewModel: CompletedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OngoingViewModel::class)
    abstract fun ongoingViewModel(ongoingViewModel: OngoingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingViewModel::class)
    abstract fun upcomingViewModel(upcomingViewModel: UpcomingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuizTestViewModel::class)
    abstract fun quizTestViewModel(quizTestViewModel: QuizTestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel::class)
    abstract fun resultViewModel(resultViewModel: ResultViewModel): ViewModel
}