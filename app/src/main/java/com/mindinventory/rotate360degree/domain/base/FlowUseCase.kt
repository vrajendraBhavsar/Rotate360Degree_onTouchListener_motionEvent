package com.app.miandroidbasestructure.domain.base

abstract class FlowUseCase<out S, in T> {

    /**
     * Exposes result of this use case
     */
    suspend operator fun invoke(params: T): S {
        return performAction(params)
    }

    /**
     * Triggers the execution of this use case
     */

    protected abstract suspend fun performAction(params: T): S

    class None
}