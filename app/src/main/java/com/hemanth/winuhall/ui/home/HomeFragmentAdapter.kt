package com.hemanth.winuhall.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *<h1>HomeFragmentAdapter</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeFragmentAdapter(
    fragment: FragmentActivity,
    private val listFragment: ArrayList<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position]

}

