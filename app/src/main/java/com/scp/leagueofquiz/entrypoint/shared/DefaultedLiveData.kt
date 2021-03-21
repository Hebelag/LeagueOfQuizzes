package com.scp.leagueofquiz.entrypoint.shared;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class DefaultedLiveData<T> extends MutableLiveData<T> {
  private final T defaultValue;

  public DefaultedLiveData(T defaultValue) {
    this.defaultValue = defaultValue;
  }

  @NonNull
  @Override
  public T getValue() {
    return super.getValue() != null ? super.getValue() : defaultValue;
  }
}
