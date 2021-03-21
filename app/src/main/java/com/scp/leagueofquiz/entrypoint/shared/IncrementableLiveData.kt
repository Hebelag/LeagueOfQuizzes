package com.scp.leagueofquiz.entrypoint.shared

import java.util.function.Consumer

class IncrementableLiveData(i: Int) : DefaultedLiveData<Int?>(i) {
    fun setIncrement() = increment { this.value = it }

    fun postIncrement() = increment { postValue(it) }

    fun increment(incrementAction: Consumer<Int?>) = incrementAction.accept(value?.plus(1))
}