package com.example.product360view.presentation.common

import android.os.Bundle
import androidx.annotation.CallSuper

abstract class Product360ViewModelActivity<T : Product360BaseViewModel> : Product360BaseActivity() {

    protected val viewModel by lazy { buildViewModel() }

    protected abstract fun buildViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLiveDataObservers()
        viewModel.loadPage()
    }

    @CallSuper
    protected open fun initLiveDataObservers() = Unit
}