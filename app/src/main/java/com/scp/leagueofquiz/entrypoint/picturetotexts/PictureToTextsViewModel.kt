package com.scp.leagueofquiz.entrypoint.picturetotexts

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.shared.Image
import com.scp.leagueofquiz.entrypoint.shared.DefaultedLiveData
import com.scp.leagueofquiz.entrypoint.shared.FetchUtil
import com.scp.leagueofquiz.entrypoint.shared.IncrementableLiveData
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.scp.leagueofquiz.repository.ChampionRepository
import com.scp.leagueofquiz.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.util.HashSet
import javax.inject.Inject

@HiltViewModel
class PictureToTextsViewModel @Inject constructor(
        private val championRepository: ChampionRepository,
        private val itemRepository: ItemRepository,
        private val fetchUtil: FetchUtil
): ViewModel() {

    lateinit var quizType: QuizType
    val imageGrid: MutableLiveData<List<Pair<String, Image>>> = MutableLiveData()
    val score = IncrementableLiveData(0)
    val failedAttempts = IncrementableLiveData(0)
    val startTime: MutableLiveData<Instant> = MutableLiveData()
    val timer = DefaultedLiveData(Duration.ZERO)
    val rightText = MutableLiveData<Pair<String, Image>>()
    val quizFinished = MutableLiveData(false)
    val buttonStartText = MutableLiveData<String>()
    private val questionsAnswered = HashSet<Pair<String,Image>>()
    val questionCount = 20

    val isQuizRunning: Boolean
        get() = startTime.value != null

    init {
        // Initialisations to be removed with dependency injection
        resetTextGrid()
    }

    private fun resetTextGrid() {
        val defaultPair = Pair(Champion.DEFAULT.name, Champion.DEFAULT.image)
        imageGrid.value = listOf(defaultPair, defaultPair, defaultPair, defaultPair)
    }

    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            timer.value = Duration.between(startTime.value, Instant.now())
            timerHandler.postDelayed(this, TIMER_REFRESH_DELAY.toMillis())
        }
    }

    fun startQuiz() {
        startTime.value = Instant.now()
        questionsAnswered.clear()
        score.value = 0
        loadTextGrid()
        timerHandler.postDelayed(timerRunnable, 0)

    }

    fun pickAnswer(instanceName: String) {
        if (instanceName == rightText.value?.first) {
            questionsAnswered.add(rightText.value!!)
            score.setIncrement()
            // Switch statement which game mode is currently played. Should be restructured in the
            // Future for our needs
            if (score.value == questionCount) {
                quizFinished.setValue(true)
            } else {
                loadTextGrid()
            }

        }
        else {
            // Add some audio or image flash here

            failedAttempts.setIncrement()
        }
    }

    private fun loadTextGrid() {
        viewModelScope.launch {

            //val randomChampions = championRepository.getRandomChampions(emptySet(),4)
            val randomTexts = mutableListOf<Pair<String,Image>>()

            repeat(4){
                when(quizType){
                    QuizType.CHAMPION -> randomTexts.add(fetchUtil.fetchChampion(championRepository))
                    QuizType.ABILITY -> randomTexts.add(fetchUtil.fetchAbility(championRepository))
                    QuizType.ITEM -> randomTexts.add(fetchUtil.fetchItem(itemRepository))
                }

                while (randomTexts.count() != randomTexts.distinct().count()){
                    randomTexts.removeAt(it)
                    when(quizType){
                        QuizType.CHAMPION -> randomTexts.add(fetchUtil.fetchChampion(championRepository))
                        QuizType.ABILITY -> randomTexts.add(fetchUtil.fetchAbility(championRepository))
                        QuizType.ITEM -> randomTexts.add(fetchUtil.fetchItem(itemRepository))
                    }
                }
            }

            rightText.postValue(selectRightText(randomTexts))
            imageGrid.postValue(randomTexts)
        }
    }

    private fun selectRightText(fetchedTexts: MutableList<Pair<String, Image>>): Pair<String, Image> {
        val rightImagePosition = (Math.random() * 4).toInt()
        return fetchedTexts[rightImagePosition]
    }



    companion object {
        private val TIMER_REFRESH_DELAY = Duration.ofMillis(500)
    }
}