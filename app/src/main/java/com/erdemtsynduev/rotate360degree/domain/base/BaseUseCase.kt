package com.app.miandroidbasestructure.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<out S, in T> where S : Any {

    abstract suspend fun execute(params: T): Flow<S>

    suspend fun subscribe(
        ioContext: CoroutineContext = Dispatchers.IO,
        params: T,
        onLoading: () -> Unit,
        onError: (Throwable) -> Unit = {},
        onSuccess: (S) -> Unit = {}
    ) {
        execute(params).onStart {
            onLoading()
        }.flowOn(ioContext).catch { throwable: Throwable ->
            onError(throwable)
        }.collect {
            onSuccess(it)
        }
    }

    class None
}