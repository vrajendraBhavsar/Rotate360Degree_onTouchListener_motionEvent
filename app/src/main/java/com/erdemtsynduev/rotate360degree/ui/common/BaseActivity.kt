package com.erdemtsynduev.rotate360degree.ui.common

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.erdemtsynduev.rotate360degree.common.enums.NetworkStatus
import com.erdemtsynduev.rotate360degree.R
import com.erdemtsynduev.rotate360degree.common.extensions.safeCollect
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var noInternetDialog: Dialog? = null
    private val viewModel: BaseViewModel by viewModels()

    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequestBuilder: NetworkRequest.Builder by lazy {
        NetworkRequest.Builder()
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.setNetworkStatus(NetworkStatus.NETWORK_ON)
            }

            override fun onLost(network: Network) {
                viewModel.setNetworkStatus(NetworkStatus.NETWORK_OFF)
            }
        }
    }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.networkStatusLiveEvent.safeCollect(::handleNetworkStatusLiveEvent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleNetworkStatusLiveEvent(networkStatus: NetworkStatus) {
        when (networkStatus) {
            NetworkStatus.NETWORK_ON -> dismissNoInternetDialog()
            NetworkStatus.NETWORK_OFF -> showNoInternetDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNoInternetDialog() {
        if (noInternetDialog == null) {
            noInternetDialog = Dialog(this)
            noInternetDialog?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_no_internet)
                setCancelable(false)
                val btnOk = this.findViewById<AppCompatButton>(R.id.btnOk)
                btnOk?.setOnClickListener {
                    checkNetworkAndShowNoInternetDialog()
                }
            }
        }
        noInternetDialog?.show()
    }

    private fun dismissNoInternetDialog() {
        if (noInternetDialog != null) {
            if (noInternetDialog!!.isShowing) {
                noInternetDialog?.dismiss()
            }
            noInternetDialog = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), networkCallback)
        checkNetworkAndShowNoInternetDialog()
        super.onResume()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNetworkAndShowNoInternetDialog() {
        if (connectivityManager.activeNetwork == null) {
            showNoInternetDialog()
        }
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    @CallSuper
    protected open fun initViews() {
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        dismissNoInternetDialog()
    }
}