package com.scp.leagueofquiz.entrypoint.quizresult

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.item.Item
import com.scp.leagueofquiz.entrypoint.shared.QuizMode
import com.scp.leagueofquiz.repository.ChampionRepository
import com.scp.leagueofquiz.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel @Inject constructor(private val itemRepo: ItemRepository) : ViewModel() {

    val itemImage: MutableLiveData<Item> = MutableLiveData()

    fun chooseImage() {
        viewModelScope.launch{
            val itemChosen = itemRepo.getRandomItem()

            itemImage.postValue(itemChosen)
        }

    }

    lateinit var quizMode: QuizMode
    val score: MutableLiveData<Int> = MutableLiveData(0)
    val failedAttempts: MutableLiveData<Int> = MutableLiveData(0)
    val timer: MutableLiveData<Duration> = MutableLiveData(Duration.ZERO)

}