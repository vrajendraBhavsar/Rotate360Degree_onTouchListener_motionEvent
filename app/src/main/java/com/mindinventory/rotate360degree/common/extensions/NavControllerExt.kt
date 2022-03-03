package com.app.miandroidbasestructure.common.extensions

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.mindinventory.rotate360degree.common.utils.buildAnimOptions

fun NavController.safeNavigate(
    @IdRes direction: Int,
    popupTo: Int? = null,
    inclusive: Boolean = false,
    options: NavOptions = buildAnimOptions(popupTo, inclusive)
) {
    navigate(direction, null, options)
}

fun NavController.safeNavigate(
    navDirections: NavDirections,
    popupTo: Int? = null,
    inclusive: Boolean = false,
    options: NavOptions = buildAnimOptions(popupTo, inclusive)
) {
    navigate(navDirections, options)
}
