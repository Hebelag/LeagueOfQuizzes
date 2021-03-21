package com.scp.leagueofquiz.entrypoint.quizresult

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import java.time.Duration

class QuizResultViewModel : ViewModel() {

    lateinit var quizMode: QuizMode
    val score: MutableLiveData<Int> = MutableLiveData(0)
    val failedAttempts: MutableLiveData<Int> = MutableLiveData(0)
    val timer: MutableLiveData<Duration> = MutableLiveData(Duration.ZERO)

}