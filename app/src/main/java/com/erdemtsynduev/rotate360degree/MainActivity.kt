package com.erdemtsynduev.rotate360degree

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.databinding.ActivityMainBinding
import com.erdemtsynduev.rotate360degree.model.DataProvider
import com.erdemtsynduev.rotate360degree.recyclerView.Image360Adapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.DebugTree
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val bottlePictureAssets: ArrayList<String> = ArrayList()
    private val carPictureAssets: ArrayList<String> = ArrayList()
    private val shoesPictureAssets: ArrayList<String> = ArrayList()

    var playImage = true
    var isReverse = true
    var indexImage = 0
    var indexImageShoes = 0
    var indexImageCar = 0
    private var x1: Float = 0f
    private var x2: Float = 0f
    private val minDistance = 60

    private val productAdapter : Image360Adapter by lazy {
        Image360Adapter(DataProvider.getProductList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        /*createListAssetsImage()
        coroutinesStartFunction()*/
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val myLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        // pass it to rvLists layoutManager
        binding.rv360List.apply {
            layoutManager = myLayoutManager
            adapter = productAdapter
        }
    }

    /*override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {    //Action Down => Finger touched the screen
                x1 = event.x    //to get touch in the X axes..when user just put down finger on screen...just touched
                playImage = false
            }
            MotionEvent.ACTION_UP -> {  //Action Up => User lifted finger up
                x2 = event.x   //to get touch in the X axes..when user lift up finger
                val deltaX = x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
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
            indexImageShoes--
            indexImageCar--
            checkNumberIndex()
            checkNumberIndexShoes()
            checkNumberIndexCar()
            runOnUiThread {
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivBottle_360.drawable)
                    .into(ivBottle_360)
                Glide.with(this).load(shoesPictureAssets[indexImageShoes])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
                Glide.with(this).load(carPictureAssets[indexImageCar])
                    .placeholder(ivCar_360.drawable)
                    .into(ivCar_360)
            }
            delay(50)
        }
    }

    private suspend fun rotateLeft(count: Int) {
        for (i in 0..count) {
            indexImage++
            indexImageShoes++
            indexImageCar++
            checkNumberIndex()
            checkNumberIndexShoes()
            checkNumberIndexCar()
            runOnUiThread {
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivBottle_360.drawable)
                    .into(ivBottle_360)
                Glide.with(this).load(shoesPictureAssets[indexImageShoes])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
                Glide.with(this).load(carPictureAssets[indexImageCar])
                    .placeholder(ivCar_360.drawable)
                    .into(ivCar_360)
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
            checkNumberIndexShoes()
            checkNumberIndexCar()
            runOnUiThread {
                Glide.with(this).load(bottlePictureAssets[indexImage])
                    .placeholder(ivBottle_360.drawable)
                    .into(ivBottle_360)
                Glide.with(this).load(shoesPictureAssets[indexImageShoes])
                    .placeholder(ivShoes_360.drawable)
                    .into(ivShoes_360)
                Glide.with(this).load(carPictureAssets[indexImageCar])
                    .placeholder(ivCar_360.drawable)
                    .into(ivCar_360)
            }
            delay(100)
            increaseIndex()
            increaseIndexShoes()
            increaseIndexCar()
        }
    }


    private fun checkNumberIndex() {
        if (indexImage < 0) {
            indexImage = 35
        } else if (indexImage > 35) {
            indexImage = 0
        }
    }

    private fun checkNumberIndexShoes() {
        if (indexImageShoes < 0) {
            indexImageShoes = 17
        } else if (indexImageShoes > 17) {
            indexImageShoes = 0
        }
    }

    private fun checkNumberIndexCar() {
        if (indexImageCar < 0) {
            indexImageCar = 51
        } else if (indexImageCar > 51) {
            indexImageCar = 0
        }
    }

    private fun increaseIndex() {
        if (isReverse) {
            indexImage--
        } else {
            indexImage++
        }
    }

    private fun increaseIndexShoes() {
        if (isReverse) {
            indexImageShoes--
        } else {
            indexImageShoes++
        }
    }

    private fun increaseIndexCar() {
        if (isReverse) {
            indexImageCar--
        } else {
            indexImageCar++
        }
    }

    private fun createListAssetsImage() {
        for(i in 2696..2731){
            bottlePictureAssets.add("file:///android_asset/bottle/AVF_${i}.jpg")
        }
        *//*Taking images from the assert folder*//*
        for (i in 52 downTo 1) {
            carPictureAssets.add("file:///android_asset/car/${i}.png")
        }
        for (i in 1..18) {
            shoesPictureAssets.add("file:///android_asset/shoes/image1_${i}.jpg")
        }
    }*/
}