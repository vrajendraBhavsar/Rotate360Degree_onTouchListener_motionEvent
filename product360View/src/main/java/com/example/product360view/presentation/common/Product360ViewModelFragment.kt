package com.example.product360view.presentation.common

import android.os.Bundle
import androidx.annotation.CallSuper

abstract class Product360ViewModelFragment<T : Product360BaseViewModel> : Product360BaseFragment() {

    protected val viewModel by lazy { buildViewModel() }

    protected abstract fun buildViewModel(): T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLiveDataObservers()
        viewModel.loadPage()
    }

    @CallSuper
    protected open fun initLiveDataObservers() {
    }

}
