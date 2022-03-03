package com.mindinventory.rotate360degree.common.extensions

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.app.miandroidbasestructure.common.extensions.safeNavigate
import com.mindinventory.rotate360degree.common.utils.buildAnimOptions


fun Fragment.safeNavigateWithFragment(
    @IdRes direction: Int,
    popupTo: Int? = null,
    inclusive: Boolean = false,
    options: NavOptions = buildAnimOptions(popupTo, inclusive)
) {
    findNavController().safeNavigate(direction, popupTo, inclusive, options)
}

