package com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager.resource

import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager.MainMenuItem
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import java.util.*

object MainMenuItems {
    val CHAMPION_QUIZ = MainMenuItem(title ="DAILY",  quizType = QuizType.CHAMPION)
    val ABILITY_QUIZ = MainMenuItem(title ="CUSTOM",  quizType = QuizType.CHAMPION)
    val ITEM_QUIZ = MainMenuItem(title ="QUIZ",  quizType = QuizType.CHAMPION)
    var mainMenuItems = ArrayList(listOf(CHAMPION_QUIZ, ABILITY_QUIZ, ITEM_QUIZ))
}