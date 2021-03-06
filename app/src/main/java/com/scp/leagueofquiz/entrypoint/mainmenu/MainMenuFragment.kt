package com.scp.leagueofquiz.entrypoint.mainmenu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.databinding.MainMenuFragmentBinding
import com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager.MainMenuItemFragment
import com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager.MainMenuPagerAdapter
import com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager.resource.MainMenuItems
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.main_menu_fragment) {
    private val viewModel: MainMenuViewModel by viewModels()
    private val binding by viewBinding(MainMenuFragmentBinding::bind)

    // widgets
    private val mMainMenuViewPager: ViewPager? = null
    private val mTabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is just a temporary place to where to initialise the database. We'll find a proper way
        // to manage this later
        viewModel.updateDatabase()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        /*binding.buttonChampQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.CHAMPION)));
    binding.buttonItemQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ITEM)));
    binding.buttonAbilityQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ABILITY)));
        binding.preferences.setOnClickListener {
            NavHostFragment.findNavController(this)
                    .navigate(MainMenuFragmentDirections.actionSettings())
        }*/
    }

    private fun init() {
        val fragments = ArrayList<Fragment>()
        val mainMenuItems = MainMenuItems.mainMenuItems
        for (mainMenuItem in mainMenuItems) {
            val fragment = MainMenuItemFragment.getInstance(mainMenuItem)
            fragments.add(fragment)
        }
        val pagerAdapter = parentFragment?.let { MainMenuPagerAdapter(it, fragments) }
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(
                binding.tabLayout, binding.viewPager, true, true) { tab: TabLayout.Tab, _: Int -> tab.text = "" }
                .attach()
    }
}