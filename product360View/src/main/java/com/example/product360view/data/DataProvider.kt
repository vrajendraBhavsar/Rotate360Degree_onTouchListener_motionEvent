package com.example.product360view.data

import com.example.product360view.domain.image.ImageType

object DataProvider {
    fun getCarImageList(): ArrayList<ImageType> {
        return ArrayList<ImageType>().apply {
            //Car - example
            for (i in 52 downTo 1) {
                add(ImageType.Asset(imageString = "file:///android_asset/car/${i}.png"))
            }
        }
    }

    fun getBottleImageList(): ArrayList<ImageType> {
        return ArrayList<ImageType>().apply {
            //Car - example
            for (i in 2696..2731) {
                add(ImageType.Asset("file:///android_asset/bottle/AVF_${i}.jpg"))
            }
        }
    }

    fun getShoesImageList(): ArrayList<ImageType> {
        return ArrayList<ImageType>().apply {
            //Car - example
            for (i in 1..18) {
                add(ImageType.Asset("file:///android_asset/shoes/image1_${i}.jpg"))
            }
        }
    }
}
