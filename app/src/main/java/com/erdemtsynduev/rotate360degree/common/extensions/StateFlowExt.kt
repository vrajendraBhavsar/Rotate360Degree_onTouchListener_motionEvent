package com.erdemtsynduev.rotate360degree.common.extensions

import com.app.miandroidbasestructure.domain.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

suspend fun <T> StateFlow<T>.safeCollect(observer: (T) -> Unit) {
    collect {
        it?.let(observer) ?: Timber.d("state flow data value is null")
    }
}

fun <T> MutableStateFlow<UiState<T>>.setLoading() {
    value = UiState.Loading()
}

fun <T> MutableStateFlow<UiState<T>>.setSuccess(data: T) {
    value = UiState.Success(data)
}

fun <T> MutableStateFlow<UiState<T>>.setError(throwable: Throwable) {
    value = UiState.Error(throwable)
}

fun <T> MutableStateFlow<UiState<T>>.isLoading() = value is UiState.Loading<T>

fun <T> StateFlow<UiState<T>>.isLoading() = value is UiState.Loading<T>