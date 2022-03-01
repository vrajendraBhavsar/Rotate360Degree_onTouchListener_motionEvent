package com.erdemtsynduev.rotate360degree.model

import android.content.res.Resources
import android.os.Parcelable
import androidx.annotation.StringRes
import com.erdemtsynduev.rotate360degree.R
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Product(
    val title: String,
    val imageList: ArrayList<String>,
    @StringRes val description: Int
) : Parcelable

object DataProvider {
    fun getProductList(): ArrayList<Product> {
        return ArrayList<Product>().apply {
            add(Product(title = "car", imageList = ArrayList<String>().apply {
                /*Taking images from the assert folder*/
                for (i in 52 downTo 1) {
                    add("file:///android_asset/car/${i}.png")
                }
            }, description = R.string.text_car_description))
            add(Product(title = "bottle", imageList = ArrayList<String>().apply {
                for (i in 2696..2731) {
                    add("file:///android_asset/bottle/AVF_${i}.jpg")
                }
            }, description =R.string.text_bottle_description))
            add(Product(title = "shoes", imageList = ArrayList<String>().apply {
                for (i in 1..18) {
                    add("file:///android_asset/shoes/image1_${i}.jpg")
                }
            }, description = R.string.text_shoes_description))
        }
    }
}