package com.erdemtsynduev.rotate360degree

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private val arrayListPictureAssets: ArrayList<String> = ArrayList()
    var playImage = true
    var isReverse = true
    var indexImage = 0
    private var x1: Float = 0f
    private var x2: Float = 0f
    private val minDistance = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        createListAssetsImage()
        coroutinesStartFunction()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = event.x
                playImage = false
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x
                val deltaX = x2 - x1
                val absDeltaX = abs(deltaX)
                if (absDeltaX > minDistance) {
                    val count = absDeltaX.toInt() / 30
                    if (x2 > x1) {
                        GlobalScope.launch {
                            rotateRight(count)
                        }
                        Timber.d("rotateRight = %s", count)
                        Timber.d("Left to Right swipe [Next]")
                    } else {
                        GlobalScope.launch {
                            rotateLeft(count)
                        }
                        Timber.d("rotateLeft = %s", count)
                        Timber.d("Right to Left swipe [Previous]")
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private suspend fun rotateRight(count: Int) {
        for (i in 0..count) {
            indexImage--
            checkNumberIndex()
            runOnUiThread {
                Glide.with(this).load(arrayListPictureAssets[indexImage])
                    .placeholder(main_activity_photo_image.drawable)
                    .into(main_activity_photo_image)
            }
            delay(50)
        }
    }

    private suspend fun rotateLeft(count: Int) {
        for (i in 0..count) {
            indexImage++
            checkNumberIndex()
            runOnUiThread {
                Glide.with(this).load(arrayListPictureAssets[indexImage])
                    .placeholder(main_activity_photo_image.drawable)
                    .into(main_activity_photo_image)
            }
            delay(50)
        }
    }

    private fun coroutinesStartFunction() {
        GlobalScope.launch {
            playImageLikeGif()

        }
    }

    private suspend fun playImageLikeGif() {
        while (playImage) {
            checkNumberIndex()
            runOnUiThread {
                Glide.with(this).load(arrayListPictureAssets[indexImage])
                    .placeholder(main_activity_photo_image.drawable)
                    .into(main_activity_photo_image)
            }
            delay(100)
            increaseIndex()
        }
    }


    private fun checkNumberIndex() {
        if (indexImage < 0) {
            indexImage = 35
        } else if (indexImage > 35) {
            indexImage = 0
        }
    }

    private fun increaseIndex() {
        if (isReverse) {
            indexImage--
        } else {
            indexImage++
        }
    }

    private fun createListAssetsImage() {
        arrayListPictureAssets.add("file:///android_asset/AVF_2696.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2697.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2698.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2699.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2700.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2701.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2702.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2703.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2704.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2705.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2706.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2707.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2708.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2709.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2710.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2711.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2712.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2713.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2714.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2715.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2716.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2717.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2718.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2719.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2720.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2721.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2722.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2723.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2724.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2725.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2726.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2727.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2728.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2729.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2730.jpg")
        arrayListPictureAssets.add("file:///android_asset/AVF_2731.jpg")
    }
}