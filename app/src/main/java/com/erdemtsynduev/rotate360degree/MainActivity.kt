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

    private val bottlePictureAssets: ArrayList<String> = ArrayList()
    private val carPictureAssets: ArrayList<String> = ArrayList()
    private val shoesPictureAssets: ArrayList<String> = ArrayList()
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
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
            }
            delay(50)
        }
    }

    private suspend fun rotateLeft(count: Int) {
        for (i in 0..count) {
            indexImage++
            checkNumberIndex()
            runOnUiThread {
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
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
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
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
        for(i in 2696..2731){
            bottlePictureAssets.add("file:///android_asset/bottle/AVF_${i}.jpg")
        }
    }
}