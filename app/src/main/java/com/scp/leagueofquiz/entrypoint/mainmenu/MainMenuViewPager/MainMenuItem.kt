package com.scp.leagueofquiz.entrypoint.mainmenu.mainMenuViewPager

import android.os.Parcelable
import com.scp.leagueofquiz.R
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import kotlinx.parcelize.Parcelize

@Parcelize
class MainMenuItem(val title: String, val pedestal_image: Int = R.drawable.pedestal_01_inventory, val light_image: Int = R.drawable.pedestal_light_shine, val quizType: QuizType) : Parcelable