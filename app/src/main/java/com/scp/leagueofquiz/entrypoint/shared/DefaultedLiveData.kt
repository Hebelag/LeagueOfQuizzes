package com.scp.leagueofquiz.entrypoint.shared

import androidx.lifecycle.MutableLiveData

open class DefaultedLiveData<T>(private val defaultValue: T) : MutableLiveData<T>() {
    override fun getValue(): T = super.getValue() ?: defaultValue
}