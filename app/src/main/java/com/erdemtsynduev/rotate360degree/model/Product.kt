package com.erdemtsynduev.rotate360degree.model

import java.security.ProtectionDomain

/*data class Item360(
    var imgLabel: String,
    var imgShoesList: ArrayList<String>,
    var imgCarList: ArrayList<String>,
    var imgBottleList: ArrayList<String>
)*/

data class Product(
    val title: String,
    val imageList: ArrayList<String>
)

object DataProvider {
    fun getProductList(): ArrayList<Product> {
        return ArrayList<Product>().apply {
            add(Product(title = "bottle", imageList = ArrayList<String>().apply {
                for (i in 2696..2731) {
                    add("file:///android_asset/bottle/AVF_${i}.jpg")
                }
            }))
            add(Product(title = "car", imageList = ArrayList<String>().apply {
                /*Taking images from the assert folder*/
                for (i in 52 downTo 1) {
                    add("file:///android_asset/car/${i}.png")
                }
            }))
            add(Product(title = "shoes", imageList = ArrayList<String>().apply {
                for (i in 1..18) {
                    add("file:///android_asset/shoes/image1_${i}.jpg")
                }
            }))
        }
    }
}