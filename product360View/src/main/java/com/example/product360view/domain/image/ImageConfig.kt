package com.example.product360view.domain.image

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.product360view.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageConfig(
    var toolbarColor: Int = Color.BLACK,
    var statusBarColor: Int = Color.BLACK,
    var toolbarResourceColor: Int = Color.WHITE,
    var progressBarColor: Int = Color.BLACK,
    @DrawableRes var placeHolder: Int = R.drawable.ic_image_placeholder,
    @DrawableRes var errorDrawable: Int = R.drawable.ic_image_placeholder,
    var listOfImages: ArrayList<String> = ArrayList(),
    var isCrop: Boolean = true
) : Parcelable {
    companion object {

        private var mediaPickerConfig = ImageConfig()

        fun setConfig(lassiConfig: ImageConfig) {
            this.mediaPickerConfig.apply {
                toolbarColor = lassiConfig.toolbarColor
                statusBarColor = lassiConfig.statusBarColor
                toolbarResourceColor = lassiConfig.toolbarResourceColor
                progressBarColor = lassiConfig.progressBarColor
                placeHolder = lassiConfig.placeHolder
                errorDrawable = lassiConfig.errorDrawable
                isCrop = lassiConfig.isCrop
            }
        }

        fun getConfig(): ImageConfig {
            return mediaPickerConfig
        }
    }
}
