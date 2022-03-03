package com.erdemtsynduev.rotate360degree.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.MainActivity
import com.erdemtsynduev.rotate360degree.databinding.FragmentProductDetailBinding
import com.erdemtsynduev.rotate360degree.model.Product
import com.erdemtsynduev.rotate360degree.ui.common.BaseFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Math.abs

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    private val TAG: String = ProductDetailFragment::class.java.simpleName
    var playImage = true
    var isReverse = true
    var indexImageBottle = 0
    var indexImageShoes = 0
    var indexImageCar = 0
    private var x1: Float = 0f
    private var x2: Float = 0f
    private var tempX2: Float = 0f
    private val minDistance = 80

    private val productDetailFragmentArgs: ProductDetailFragmentArgs by navArgs()

    override fun getClassName(): String = ProductDetailFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.tbProductDetails.ibBack.setOnClickListener(::navigateToProductFragment)
        setToolbarTitle("Product Detail")
        bindData()
//        setUpGestureDetector()
        init360Image(product = productDetailFragmentArgs.product)
    }

    private fun navigateToProductFragment(view: View?) {
        findNavController().popBackStack()
    }

    private fun bindData() {
        binding.tvProductName.text = productDetailFragmentArgs.product.title
        binding.tvProductDescription.text =
            requireContext().getText(productDetailFragmentArgs.product.description)
    }

    //...

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("ClickableViewAccessibility")
    private fun init360Image(product: Product) {
        with(this.binding)
        {
            Glide.with(this.root.context).load(product.imageList[0])
                .placeholder(ivProductImage.drawable)
                .into(ivProductImage)

            coroutinesStartFunction(root.context, this.root, product)

            /*val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
                override fun onShowPress(event: MotionEvent?) {
                }

                override fun onSingleTapUp(event: MotionEvent?): Boolean {
                    return true
                }

                override fun onSingleTapConfirmed(event: MotionEvent?): Boolean {
                    return true
                }

                override fun onDown(event: MotionEvent?): Boolean {
                    return true
                }

                override fun onFling(event1: MotionEvent?, event2: MotionEvent?, velocityX: Float,
                                     velocityY: Float): Boolean {
                    Log.d(TAG, "onFling: (event1, event2)=>($event1,$event2")
                    return true
                }

                override fun onScroll(event1: MotionEvent?, event2: MotionEvent?, distanceX: Float,
                                      distanceY: Float): Boolean {
                    Log.d(TAG, "onScroll: (event1, event2)=>($event1,$event2")
                    return true
                }

                override fun onLongPress(event: MotionEvent?) {
                }

                override fun onDoubleTap(event: MotionEvent?): Boolean {
                    return true
                }

                override fun onDoubleTapEvent(event: MotionEvent?): Boolean {
                    return true
                }

                override fun onContextClick(event: MotionEvent?): Boolean {
                    return true
                }

                *//*fun MotionEvent?.description(description: String): String {
                    return if (this == null) "Empty press" else "$description at (${x.round()}, ${y.round()})"
                }*//*
            }
            val gestureDetector = GestureDetector(requireContext(), gestureListener)

            binding.root.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
                    when (motionEvent?.action) {
                        MotionEvent.ACTION_DOWN -> {    //Action Down => Finger touched the screen
                            x1 = motionEvent.x    //to get touch in the X axes..when user just put down finger on screen...just touched
                            playImage = false
                        }

                        MotionEvent.ACTION_MOVE -> {
                            x2 = motionEvent.x   //to get touch in the X axes..when user lift up finger
                            tempX2 = x2
                            if (x2 != 0f) {
                                if (tempX2 != x2) {
                                    val deltaX = x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
                                    val absDeltaX = abs(deltaX)
                                    if (absDeltaX > minDistance) {
                                        val count = absDeltaX.toInt() / 60 //150
//                                val count = (countAbs.toDouble() / 2).roundToInt()
                                        if (x2 > x1) {
                                            GlobalScope.launch {
                                                rotateRight(count, root.context, v, product)
                                            }
                                            Timber.d("rotateRight count= %s", count)
                                            Timber.d("Left to Right swipe [Next]")
                                        } else {
                                            GlobalScope.launch {
                                                rotateLeft(count, root.context, v, product)
                                            }
                                            Timber.d("rotateLeft = %s", count)
                                            Timber.d("Right to Left swipe [Previous]")
                                        }
                                    }
                                }
                            }
                        }

                        *//*MotionEvent.ACTION_UP -> {  //Action Up => User lifted finger up
                            x2 =
                                motionEvent.x   //to get touch in the X axes..when user lift up finger
                            val deltaX =
                                x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
                            val absDeltaX = abs(deltaX)
                            if (absDeltaX > minDistance) {
                                val count = absDeltaX.toInt() / 30
                                if (x2 > x1) {
                                    GlobalScope.launch {
                                        rotateRight(count, root.context, v, product)
                                    }
                                    Timber.d("rotateRight count= %s", count)
                                    Timber.d("Left to Right swipe [Next]")
                                } else {
                                    GlobalScope.launch {
                                        rotateLeft(count, root.context, v, product)
                                    }
                                    Timber.d("rotateLeft = %s", count)
                                    Timber.d("Right to Left swipe [Previous]")
                                }
                            }
                        }*//*

                    }
                    return gestureDetector.onTouchEvent(motionEvent)
                }
            })*/

            GlobalScope.launch {
                sbImgRotation.max = product.imageList.size
                sbImgRotation.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        Toast.makeText(requireContext(), "discrete seekbar progress: $progress", Toast.LENGTH_SHORT).show()
                        if (progress > 0 && progress < product.imageList.size) {
                            context?.let {
                                Glide.with(it)
                                    .asBitmap()
                                    .load(product.imageList[progress])
                                    .placeholder(binding.ivProductImage.drawable)
                                    .into(binding.ivProductImage)
                            }
                        }

                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {

                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {

                    }
                })
            }

/*            cvCharacterInfo.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
                    when (motionEvent?.action) {
                        MotionEvent.ACTION_DOWN -> {    //Action Down => Finger touched the screen
                            x1 = motionEvent.x    //to get touch in the X axes..when user just put down finger on screen...just touched
                            playImage = false
                        }

                        MotionEvent.ACTION_UP -> {
                            x2 = motionEvent.x   //to get touch in the X axes..when user lift up finger
                            val deltaX = x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
                            val absDeltaX = abs(deltaX)
                            if (absDeltaX > minDistance) {
                                val count = absDeltaX.toInt() / 60 //150
//                                val count = (countAbs.toDouble() / 2).roundToInt()
                                if (x2 > x1) {
                                    GlobalScope.launch {
                                        rotateRight(count, root.context, v, product)
                                    }
                                    Timber.d("rotateRight count= %s", count)
                                    Timber.d("Left to Right swipe [Next]")
                                } else {
                                    GlobalScope.launch {
                                        rotateLeft(count, root.context, v, product)
                                    }
                                    Timber.d("rotateLeft = %s", count)
                                    Timber.d("Right to Left swipe [Previous]")
                                }
                            }
                        }

                        *//*MotionEvent.ACTION_UP -> {  //Action Up => User lifted finger up
                            x2 =
                                motionEvent.x   //to get touch in the X axes..when user lift up finger
                            val deltaX =
                                x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
                            val absDeltaX = abs(deltaX)
                            if (absDeltaX > minDistance) {
                                val count = absDeltaX.toInt() / 30
                                if (x2 > x1) {
                                    GlobalScope.launch {
                                        rotateRight(count, root.context, v, product)
                                    }
                                    Timber.d("rotateRight count= %s", count)
                                    Timber.d("Left to Right swipe [Next]")
                                } else {
                                    GlobalScope.launch {
                                        rotateLeft(count, root.context, v, product)
                                    }
                                    Timber.d("rotateLeft = %s", count)
                                    Timber.d("Right to Left swipe [Previous]")
                                }
                            }
                        }*//*

                    }
                    return true
                }
            })*/
        }
    }

    private suspend fun rotateRight(
        count: Int,
        context: Context,
        item: View?,
        product: Product
    ) {
        for (i in 0..count) {
            (context as MainActivity).runOnUiThread {
                item?.let {
                    when (product.title) {
                        "bottle" -> {
                            indexImageBottle--
                            checkNumberIndex()

                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImageBottle])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "car" -> {
                            indexImageCar--
                            checkNumberIndexCar()

                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImageCar])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "shoes" -> {
                            indexImageShoes--
                            checkNumberIndexShoes()

                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImageShoes])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        else -> Timber.d("rotateRight: Else branch")
                    }
                }
            }
            delay(40)
        }
    }

    private suspend fun rotateLeft(
        count: Int,
        context: Context,
        item: View?,
        product: Product
    ) {
        for (i in 0..count) {

            (context as MainActivity).runOnUiThread {
                item?.let {
                    when (product.title) {
                        "bottle" -> {
                            indexImageBottle++
                            checkNumberIndex()

                            Glide.with(context)
                                .load(product.imageList[indexImageBottle])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "car" -> {
                            indexImageCar++
                            checkNumberIndexCar()

                            Glide.with(context)
                                .load(product.imageList[indexImageCar])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "shoes" -> {
                            indexImageShoes++
                            checkNumberIndexShoes()

                            Glide.with(context)
                                .load(product.imageList[indexImageShoes])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        else -> Timber.d("rotateLeft: Else branch")
                    }
                }
            }
            delay(40)
        }
    }

    private fun coroutinesStartFunction(context: Context, item: View?, product: Product) {
        GlobalScope.launch {
//            playImageLikeGif(context, item, product)
        }
    }

    private suspend fun playImageLikeGif(context: Context, item: View?, product: Product) {
        while (playImage) {
            checkNumberIndex()
            checkNumberIndexShoes()
            checkNumberIndexCar()
            (context as MainActivity).runOnUiThread {
                item?.let {
                    when (product.title) {
                        "bottle" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageBottle])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "car" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageCar])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        "shoes" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageShoes])
                                .placeholder(binding.ivProductImage.drawable)
                                .into(binding.ivProductImage)
                        }
                        else -> Timber.d("rotateLeft: Else branch")
                    }
//                it.ivItem.loadImage(productList[0].imageList[indexImage])
                }
            }
            delay(200)
            increaseIndex()
            increaseIndexShoes()
            increaseIndexCar()
        }
    }


    private fun checkNumberIndex() {
        if (indexImageBottle < 0) {
            indexImageBottle = 35
        } else if (indexImageBottle > 35) {
            indexImageBottle = 0
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
            indexImageBottle--
        } else {
            indexImageBottle++
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

/*    private fun setUpGestureDetector() {
        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return true
            }
        }
        val gestureDetector = GestureDetector(requireContext(), gestureListener)
    }*/

    private fun inflateGlide(context: Context, item: View, product: Product) {
        item.let {
            Glide.with(context)
                .load(product.imageList[indexImageBottle])
                .placeholder(binding.ivProductImage.drawable)
                .into(binding.ivProductImage)
        }
    }
}