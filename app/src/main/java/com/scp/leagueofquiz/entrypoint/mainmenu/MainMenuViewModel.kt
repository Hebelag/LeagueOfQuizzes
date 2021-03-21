package com.scp.leagueofquiz.entrypoint.mainmenu;

import androidx.lifecycle.ViewModel;
import com.scp.leagueofquiz.repository.MetadataRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class MainMenuViewModel extends ViewModel {
  private final MetadataRepository metadataRepository;

  @Inject
  public MainMenuViewModel(MetadataRepository metadataRepository) {
    this.metadataRepository = metadataRepository;
  }

  public void updateDatabase() {
    metadataRepository.startDatabaseUpdate();
  }
}
