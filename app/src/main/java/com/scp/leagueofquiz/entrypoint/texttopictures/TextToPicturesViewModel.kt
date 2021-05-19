package com.scp.leagueofquiz.entrypoint.texttopictures

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.shared.Image
import com.scp.leagueofquiz.entrypoint.shared.DefaultedLiveData
import com.scp.leagueofquiz.entrypoint.shared.IncrementableLiveData
import com.scp.leagueofquiz.entrypoint.shared.QuizType
import com.scp.leagueofquiz.repository.ChampionRepository
import com.scp.leagueofquiz.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TextToPicturesViewModel @Inject constructor(
        private val championRepository: ChampionRepository,
        private val itemRepository: ItemRepository) : ViewModel() {
    // Accessors
    // Static data
    private var questionCount: Int? = 20
    private val questionsAnswered = HashSet<Pair<String,Image>>()

    // Mutable data
    var quizType: String = ""
    val imageGrid: MutableLiveData<List<Pair<String,Image>>> = MutableLiveData()
    val score = IncrementableLiveData(0)
    val failedAttempts = IncrementableLiveData(0)
    val startTime: MutableLiveData<Instant> = MutableLiveData()
    val timer = DefaultedLiveData(Duration.ZERO)
    val rightImage = MutableLiveData<Pair<String,Image>>()
    val quizFinished = MutableLiveData(false)
    val buttonText = MutableLiveData<String>()

    init {
        // Initialisations to be removed with dependency injection
        resetImageGrid()
    }

    // Other
    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            timer.value = Duration.between(startTime.value, Instant.now())
            timerHandler.postDelayed(this, TIMER_REFRESH_DELAY.toMillis())
        }
    }

    private fun resetImageGrid() {
        // Reset the champion images to the default image
        var defaultPair = Pair(Champion.DEFAULT.name,Champion.DEFAULT.image)
        imageGrid.value = listOf(defaultPair, defaultPair, defaultPair, defaultPair)
    }

    fun startQuiz() {
        //Set the start time to the current time
        startTime.value = Instant.now()

        //Clear the set of already answered champions
        //Can be deleted in the future probably
        questionsAnswered.clear()
        score.value = 0
        loadImageGrid()

        //The timer, which can be deleted in the future too according to our plans
        timerHandler.postDelayed(timerRunnable, 0)
    }

    val isQuizRunning: Boolean
        get() = startTime.value != null

    //pickAnswer get's called on the champion image click, champIndex indicating the position
    fun pickAnswer(champIndex: Int) {
        val grid = imageGrid.value ?: throw RuntimeException("Grid is empty!")
        val imageChosen = grid[champIndex - 1]
        // If answer is right:
        if (imageChosen == rightImage.value) {
            questionsAnswered.add(imageChosen)
            score.setIncrement()
            // Switch statement which game mode is currently played. Should be restructured in the
            // Future for our needs
            if (questionsAnswered.size == questionCount) {
                quizFinished.setValue(true)
            } else {
                loadImageGrid()
            }

        }
        // If it's wrong.
        else {
            // Add some audio or image flash here

            failedAttempts.setIncrement()
        }
    }

    // Asynchronous function to load the champions into the main grid.
    private fun loadImageGrid() {
        viewModelScope.launch {

            //val randomChampions = championRepository.getRandomChampions(emptySet(),4)
            val randomImages = mutableListOf<Pair<String,Image>>()

            repeat(4){
                when(quizType){
                    "Champion" -> randomImages.add(fetchChampion())
                    "Ability" -> randomImages.add(fetchAbility())
                    "Item" -> randomImages.add(fetchItem())
                }

                while (randomImages.count() != randomImages.distinct().count()){
                    randomImages.removeAt(it)
                    var fetchedInstance = championRepository.getRandomChampion(emptySet())
                    var fetchedInstancePair = Pair(fetchedInstance.name,fetchedInstance.image)
                    randomImages.add(fetchedInstancePair)
                }
            }

            rightImage.postValue(selectRightImage(randomImages))
            imageGrid.postValue(randomImages)
        }
    }

    private suspend fun fetchItem(): Pair<String, Image> {
        var item = itemRepository.getRandomItem()
        return Pair(item.name, item.image)
    }

    private fun fetchAbility(): Pair<String, Image> {
        // MAKE NEW DATABASE FUNCTION IN CHAMPIONDAO
        return Pair(Champion.DEFAULT.name,Champion.DEFAULT.image)
    }

    private suspend fun fetchChampion(): Pair<String,Image> {
        var champion = championRepository.getRandomChampion(emptySet())
        return Pair(champion.name,champion.image)
    }

    // One of the four champions needs to be the right one. This randomizer chooses which one it is
    private fun selectRightImage(fetchedImages: List<Pair<String,Image>>): Pair<String,Image> {
        val rightImagePosition = (Math.random() * 4).toInt()
        return fetchedImages[rightImagePosition]
    }

    // Questionable setter in Kotlin?
    fun setChampionCount(championCount: Int?) {
        this.questionCount = championCount
    }

    // Static initializer to the top?
    companion object {
        private val TIMER_REFRESH_DELAY = Duration.ofMillis(500)
    }
}