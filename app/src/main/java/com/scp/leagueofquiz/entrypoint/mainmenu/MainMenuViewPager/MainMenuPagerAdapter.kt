package com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

/*
Plan for the main menu:
Since there are multiple "tabs" where the user can navigate, it should be expandable until infinity
FragmentStatePagerAdapter suits perfectly (Coding with Mitch)
Every Fragment needs: Image, listener, title (, progress?, rank?)

*/
class MainMenuPagerAdapter(fragment: Fragment, private val mFragments: ArrayList<Fragment>) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }
}