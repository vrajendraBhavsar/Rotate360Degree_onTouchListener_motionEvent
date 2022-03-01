package com.erdemtsynduev.rotate360degree.ui.common

import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar

interface ConfigureToolbar {
    fun setUpToolbar(toolbar: Toolbar)
    fun getToolbar(): Toolbar?
    fun setUpActionBar(actionBar: ActionBar)
    fun setToolbarTitle(title: String)
}
