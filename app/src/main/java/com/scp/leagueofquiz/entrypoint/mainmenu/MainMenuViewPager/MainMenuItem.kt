package com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager

import android.os.Parcelable
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import kotlinx.parcelize.Parcelize

@Parcelize
class MainMenuItem(val title: String, val image: Int = R.drawable.empty_pedestal_2, val quizType: QuizType) : Parcelable