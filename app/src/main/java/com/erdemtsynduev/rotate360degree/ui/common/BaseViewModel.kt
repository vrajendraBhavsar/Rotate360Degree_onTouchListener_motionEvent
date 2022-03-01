package com.erdemtsynduev.rotate360degree.ui.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.erdemtsynduev.rotate360degree.common.enums.NetworkStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {

    private var isFirst = true
    private var _networkStatusLiveEvent: MutableStateFlow<NetworkStatus> =
        MutableStateFlow(NetworkStatus.NETWORK_OFF)
    val networkStatusLiveEvent get() = _networkStatusLiveEvent.asStateFlow()

    /**
     * Only can call once per lifecycle
     * @param multipleTimes (OPTIONAL) set it to true to make multiple call capability
     */
    @CallSuper
    open fun loadPage(multipleTimes: Boolean = false): Boolean {
        if (isFirst) {
            isFirst = false
            return true
        }

        return isFirst || multipleTimes
    }

    fun setNetworkStatus(networkStatus: NetworkStatus){
        _networkStatusLiveEvent.value = networkStatus
    }

    override fun onCleared() {
        super.onCleared()
    }
}