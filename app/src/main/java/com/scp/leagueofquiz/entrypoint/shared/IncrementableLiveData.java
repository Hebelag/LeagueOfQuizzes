package com.scp.leagueofquiz.entrypoint.shared;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import java.util.function.Consumer;

public class IncrementableLiveData extends DefaultedLiveData<Integer> {

  public IncrementableLiveData(int i) {
    super(i);
  }

  public void setIncrement() {
    increment(this::setValue);
  }

  public void postIncrement() {
    increment(this::postValue);
  }

  public void increment(Consumer<Integer> newValue) {
    newValue.accept(getValue() + 1);
  }
}
