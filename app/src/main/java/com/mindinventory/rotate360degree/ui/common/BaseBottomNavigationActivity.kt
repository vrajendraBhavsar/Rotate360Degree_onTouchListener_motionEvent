package com.mindinventory.rotate360degree.ui.common

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseBottomNavigationActivity<VB : ViewBinding> :
    BaseActivity<VB>() {

    var bottomNavigationView: BottomNavigationView? = null
    lateinit var navController: NavController

    override fun initViews() {
        super.initViews()
        val navHostFragment =
            supportFragmentManager.findFragmentById(navHostFragment()) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(bottomNavigationView())
        bottomNavigationView?.setupWithNavController(navController)
    }

    abstract fun navHostFragment(): Int
    abstract fun bottomNavigationView(): Int
}