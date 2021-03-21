package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager

import android.os.Parcelable
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import kotlinx.parcelize.Parcelize

@Parcelize
class MainMenuItem(val title: String, val image: Int, val quizType: QuizType) : Parcelable