package com.erdemtsynduev.rotate360degree.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.MainActivity
import com.erdemtsynduev.rotate360degree.databinding.FragmentProductDetailBinding
import com.erdemtsynduev.rotate360degree.model.Product
import com.erdemtsynduev.rotate360degree.ui.common.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs

class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {
    var playImage = true
    var isReverse = true
    var indexImage = 0
    var indexImageShoes = 0
    var indexImageCar = 0
    private var x1: Float = 0f
    private var x2: Float = 0f
    private val minDistance = 60

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

    @SuppressLint("ClickableViewAccessibility")
    private fun init360Image(product: Product) {
        with(this.binding)
        {
            tvProductName.text = product.title
//                ivItem.loadImage(product.imageList[0])
            Glide.with(this.root.context).load(product.imageList[0])
                .placeholder(ivProductImage.drawable)
                .into(ivProductImage)

            coroutinesStartFunction(root.context, this.root, product)

            cvCharacterInfo.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
                    when (motionEvent?.action) {
                        MotionEvent.ACTION_DOWN -> {    //Action Down => Finger touched the screen
                            x1 = motionEvent.x    //to get touch in the X axes..when user just put down finger on screen...just touched
                            playImage = false
                        }

                        MotionEvent.ACTION_MOVE -> {
                            x2 = motionEvent.x   //to get touch in the X axes..when user lift up finger
                            val deltaX = x2 - x1 //user lift finger - put down finger => Direction in which user swiped finger
                            val absDeltaX = abs(deltaX)
                            if (absDeltaX > minDistance) {
                                val count = absDeltaX.toInt() / 60 //150
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

                        MotionEvent.ACTION_UP -> {  //Action Up => User lifted finger up
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
                        }

                    }
                    return true
                }
            })
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
                            indexImage--
                            checkNumberIndex()

                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImage])
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
            delay(150)
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
                            indexImage++
                            checkNumberIndex()

                            Glide.with(context)
                                .load(product.imageList[indexImage])
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
            delay(170)
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
                                .load(product.imageList[indexImage])
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

    private fun inflateGlide(context: Context, item: View, product: Product) {
        item.let {
            Glide.with(context)
                .load(product.imageList[indexImage])
                .placeholder(binding.ivProductImage.drawable)
                .into(binding.ivProductImage)
        }
    }
}