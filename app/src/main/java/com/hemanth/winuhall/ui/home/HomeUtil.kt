package com.hemanth.winuhall.ui.home

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hemanth.winuhall.data.model.Completed
import com.hemanth.winuhall.data.model.Ongoing
import com.hemanth.winuhall.data.model.Upcoming
import com.hemanth.winuhall.di.scope.ActivityScoped
import com.hemanth.winuhall.ui.home.completed.CompletedFragment
import com.hemanth.winuhall.ui.home.model.QuizBranchAdapter
import com.hemanth.winuhall.ui.home.ongoing.OngoingFragment
import com.hemanth.winuhall.ui.home.upcoming.UpcomingFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named
import kotlin.collections.ArrayList

@Module
class HomeUtil {

    companion object {
        const val ON_GOING = "ongoing"
        const val UP_COMING = "upcoming"
        const val COMPLETED = "completed"
    }

    @ActivityScoped
    @Provides
    fun provideCompletedFragment() = CompletedFragment()

    @ActivityScoped
    @Provides
    fun provideOngoingFragment() = OngoingFragment()

    @ActivityScoped
    @Provides
    fun provideUpcomingFragment() = UpcomingFragment()

    @ActivityScoped
    @Provides
    fun provideTabFragments(
        frag1: OngoingFragment,
        frag2: UpcomingFragment,
        frag3: CompletedFragment
    ): ArrayList<Fragment> = ArrayList(listOf(frag1, frag2, frag3))


    @ActivityScoped
    @Provides
    fun provideFragmentAdapter(activity: Activity, fragList: ArrayList<Fragment>) =
        HomeFragmentAdapter(activity as FragmentActivity, fragList)

    @ActivityScoped
    @Provides
    fun provideOngoingList() = ArrayList<Ongoing>()

    @ActivityScoped
    @Provides
    fun provideUpcomingList() = ArrayList<Upcoming>()

    @ActivityScoped
    @Provides
    fun provideCompletedList() = ArrayList<Completed>()

    @Named(ON_GOING)
    @ActivityScoped
    @Provides
    fun provideQuizBranchOngoingAdapter(
        ongoingList: ArrayList<Ongoing>,
        upcoming: ArrayList<Upcoming>,
        completed: ArrayList<Completed>
    ) = QuizBranchAdapter(ongoingList, upcoming, completed, ON_GOING)

    @Named(UP_COMING)
    @ActivityScoped
    @Provides
    fun provideQuizBranchUpcomingAdapter(
        ongoingList: ArrayList<Ongoing>,
        upcoming: ArrayList<Upcoming>,
        completed: ArrayList<Completed>
    ) = QuizBranchAdapter(ongoingList, upcoming, completed, UP_COMING)

    @Named(COMPLETED)
    @ActivityScoped
    @Provides
    fun provideQuizBranchCompletedAdapter(
        ongoingList: ArrayList<Ongoing>,
        upcoming: ArrayList<Upcoming>,
        completed: ArrayList<Completed>
    ) = QuizBranchAdapter(ongoingList, upcoming, completed, COMPLETED)

}