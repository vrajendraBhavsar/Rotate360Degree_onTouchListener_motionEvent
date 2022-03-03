package com.mindinventory.rotate360degree

import android.view.LayoutInflater
import com.mindinventory.rotate360degree.databinding.ActivityMainBinding
import com.mindinventory.rotate360degree.ui.common.BaseActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)
}