package com.example.product360view.presentation.builder

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.product360view.domain.image.ImageConfig


class Product360View(private val context: Context) {

    private var imageConfig = ImageConfig()

    /**
     * Set toolbar color resource
     */
    fun setToolbarColor(@ColorRes toolbarColor: Int): Product360View {
        imageConfig.toolbarColor = ContextCompat.getColor(context, toolbarColor)
        return this
    }

    /**
     * Set toolbar color hex
     */
    fun setToolbarColor(toolbarColor: String): Product360View {
        imageConfig.toolbarColor = Color.parseColor(toolbarColor)
        return this
    }

    /**
     * Set statusBar color resource (Only applicable for >= Lollipop)
     */
    fun setStatusBarColor(@ColorRes statusBarColor: Int): Product360View {
        imageConfig.statusBarColor = ContextCompat.getColor(context, statusBarColor)
        return this
    }

    /**
     * Set statusBar color hex (Only applicable for >= Lollipop)
     */
    fun setStatusBarColor(statusBarColor: String): Product360View {
        imageConfig.statusBarColor = Color.parseColor(statusBarColor)
        return this
    }

    /**
     * Set toolbar color resource
     */
    fun setToolbarResourceColor(@ColorRes toolbarResourceColor: Int): Product360View {
        imageConfig.toolbarResourceColor = ContextCompat.getColor(context, toolbarResourceColor)
        return this
    }

    /**
     * Set toolbar color hex
     */
    fun setToolbarResourceColor(toolbarResourceColor: String): Product360View {
        imageConfig.toolbarResourceColor = Color.parseColor(toolbarResourceColor)
        return this
    }

    /**
     * Set progressbar color resource
     */
    fun setProgressBarColor(@ColorRes progressBarColor: Int): Product360View {
        imageConfig.progressBarColor = ContextCompat.getColor(context, progressBarColor)
        return this
    }

    /**
     * Set progressbar color hex
     */
    fun setProgressBarColor(progressBarColor: String): Product360View {
        imageConfig.progressBarColor = Color.parseColor(progressBarColor)
        return this
    }

    /**
     * Set place holder to grid items
     */
    fun setPlaceHolder(@DrawableRes placeHolder: Int): Product360View {
        imageConfig.placeHolder = placeHolder
        return this
    }

    /**
     * Set error drawable to grid items
     */
    fun setErrorDrawable(@DrawableRes errorDrawable: Int): Product360View {
        imageConfig.errorDrawable = errorDrawable
        return this
    }

/*    *//**
     * Set selection drawable to grid items
     *//*
    fun setSelectionDrawable(@DrawableRes selectionDrawable: Int): Product360View {
        imageConfig.selectionDrawable = selectionDrawable
        return this
    }*/

    /**
     * Set selection drawable to grid items
     */
    fun setListOfImagesString(imageList: ArrayList<String>): Product360View {
        imageConfig.listOfImages = imageList
        return this
    }

//    /**
//     * Create intent for LassiMediaPickerActivity with config
//     */
//    fun build(): Intent {
//        ImageConfig.setConfig(imageConfig)
//        return Intent(context, LassiMediaPickerActivity::class.java)
//    }
}
