package com.scp.leagueofquiz.entrypoint.championquiz

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.entrypoint.shared.DefaultedLiveData
import com.scp.leagueofquiz.entrypoint.shared.IncrementableLiveData
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.scp.leagueofquiz.repository.ChampionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChampionQuizViewModel @Inject constructor(
        private val championRepository: ChampionRepository) : ViewModel() {
    // Accessors
    // Static data
    lateinit var quizMode: QuizMode
    private var championCount: Int? = null
    private val championsAnswered = HashSet<Champion>()

    // Mutable data
    val championGrid: MutableLiveData<List<Champion>> = MutableLiveData()
    val score = IncrementableLiveData(0)
    val failedAttempts = IncrementableLiveData(0)
    val startTime: MutableLiveData<Instant> = MutableLiveData()
    val timer = DefaultedLiveData(Duration.ZERO)
    val rightChampion = MutableLiveData<Champion>()
    val quizFinished = MutableLiveData(false)
    val buttonText = MutableLiveData<String>()

    init {
        // Initialisations to be removed with dependency injection
        resetChampionGrid()
    }

    // Other
    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnableTraining: Runnable = object : Runnable {
        override fun run() {
            when (quizMode) {
                QuizMode.TRAINING, QuizMode.ENDLESS, QuizMode.MARATHON -> timer.setValue(Duration.between(startTime.value, Instant.now()))
                QuizMode.TIME -> timer.setValue(timer.value.minus(TIMER_REFRESH_DELAY))
            }
            timerHandler.postDelayed(this, TIMER_REFRESH_DELAY.toMillis())
        }
    }

    private fun resetChampionGrid() {
        championGrid.value = listOf(Champion.DEFAULT, Champion.DEFAULT, Champion.DEFAULT, Champion.DEFAULT)
    }

    fun startQuiz() {
        startTime.value = Instant.now()
        championsAnswered.clear()
        score.value = 0
        loadChampionGrid()
        timerHandler.postDelayed(timerRunnableTraining, 0)
    }

    val isQuizRunning: Boolean
        get() = startTime.value != null

    fun pickAnswer(champIndex: Int) {
        val grid = championGrid.value ?: throw RuntimeException("Grid is empty!")
        val championChosen = grid[champIndex - 1]
        if (championChosen == rightChampion.value) {
            championsAnswered.add(championChosen)
            score.setIncrement()
            when (quizMode) {
                QuizMode.TRAINING, QuizMode.MARATHON -> if (championsAnswered.size == championCount) {
                    quizFinished.setValue(true)
                } else {
                    loadChampionGrid()
                }

                QuizMode.ENDLESS, QuizMode.TIME -> loadChampionGrid()
            }
        } else {
            failedAttempts.setIncrement()
        }
    }

    private fun loadChampionGrid() {
        viewModelScope.launch {
            val randomChampions = championRepository.getRandomChampions(championsAnswered, 4)
            rightChampion.postValue(selectRightChampion(randomChampions))
            championGrid.postValue(randomChampions)
        }
    }

    private fun selectRightChampion(buttonChampions: List<Champion>): Champion {
        val rightChampionPosition = (Math.random() * 4).toInt()
        return buttonChampions[rightChampionPosition]
    }

    fun setChampionCount(championCount: Int?) {
        this.championCount = championCount
    }

    companion object {
        private val TIMER_REFRESH_DELAY = Duration.ofMillis(500)
    }
}