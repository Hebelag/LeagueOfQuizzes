package com.scp.leagueofquiz.entrypoint.mainmenu

import androidx.lifecycle.ViewModel
import com.scp.leagueofquiz.repository.MetadataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(private val metadataRepository: MetadataRepository) : ViewModel() {
    fun updateDatabase() {
        metadataRepository.startDatabaseUpdate()
    }
}