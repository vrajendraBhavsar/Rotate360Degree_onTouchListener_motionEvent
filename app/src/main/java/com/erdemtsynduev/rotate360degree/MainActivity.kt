package com.erdemtsynduev.rotate360degree

import android.view.LayoutInflater
import com.erdemtsynduev.rotate360degree.databinding.ActivityMainBinding
import com.erdemtsynduev.rotate360degree.ui.common.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}