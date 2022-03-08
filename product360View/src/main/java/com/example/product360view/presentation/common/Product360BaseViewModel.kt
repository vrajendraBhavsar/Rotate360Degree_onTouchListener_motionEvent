package com.example.product360view.presentation.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Product360BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @CallSuper
    open fun loadPage() {

    }

    protected fun Disposable.collect() = compositeDisposable.add(this)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}