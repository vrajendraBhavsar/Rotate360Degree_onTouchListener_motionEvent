package com.erdemtsynduev.rotate360degree.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView

abstract class BaseNavigationDrawerActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var navController: NavController
    var navView: NavigationView? = null
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @CallSuper
    protected open fun initViews() {
        navController = findNavController(navHostFragment())
        navView = findViewById(navigationDrawer())
        navView?.setupWithNavController(navController)

    }

    abstract fun navHostFragment(): Int
    abstract fun navigationDrawer(): Int
    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}