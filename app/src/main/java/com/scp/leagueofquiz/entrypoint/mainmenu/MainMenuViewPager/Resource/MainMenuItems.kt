package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.Resource

import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.MainMenuItem
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import java.util.*

object MainMenuItems {
    val CHAMPION_QUIZ = MainMenuItem("Champion Quiz", R.drawable.main_menu_champ, QuizType.CHAMPION)
    val ABILITY_QUIZ = MainMenuItem("Ability Quiz", R.drawable.main_menu_champ, QuizType.ABILITY)
    val ITEM_QUIZ = MainMenuItem("Item Quiz", R.drawable.main_menu_champ, QuizType.ITEM)
    var mainMenuItems = ArrayList(listOf(CHAMPION_QUIZ, ABILITY_QUIZ, ITEM_QUIZ))
}