package com.erdemtsynduev.rotate360degree.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.MainActivity
import com.erdemtsynduev.rotate360degree.databinding.SimpleImage360Binding
import com.erdemtsynduev.rotate360degree.model.Product
import kotlinx.android.synthetic.main.simple_image_360.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs


class Image360Adapter(
    private var productList: List<Product>
) :
    RecyclerView.Adapter<Image360Adapter.ImageViewHolder>() {
    var playImage = true
    var isReverse = true
    var indexImage = 0
    var indexImageShoes = 0
    var indexImageCar = 0
    private var x1: Float = 0f
    private var x2: Float = 0f
    private val minDistance = 60

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_image_360, parent, false)
        val binding =
            SimpleImage360Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ImageViewHolder(private val simpleImage360Binding: SimpleImage360Binding) :
        RecyclerView.ViewHolder(simpleImage360Binding.root) {
        @SuppressLint("ClickableViewAccessibility")
        fun bind(product: Product) {

            with(simpleImage360Binding) {
                tvItemLabel.text = product.title
//                ivItem.loadImage(product.imageList[0])
                Glide.with(this.root.context).load(product.imageList[0])
                    .placeholder(ivItem.drawable)
                    .into(ivItem)

                coroutinesStartFunction(root.context, this.root, product)

                cv1.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View?, motionEvent: MotionEvent?): Boolean {
/*
                        touchListener.onTouched(v, product)
*/
                        when (motionEvent?.action) {
                            MotionEvent.ACTION_DOWN -> {    //Action Down => Finger touched the screen
                                x1 =
                                    motionEvent.x    //to get touch in the X axes..when user just put down finger on screen...just touched
                                playImage = false
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


    }

    //.........

    private suspend fun rotateRight(count: Int, context: Context, item: View?, product: Product) {
        for (i in 0..count) {
            indexImage--
            indexImageShoes--
            indexImageCar--
            checkNumberIndex()
            checkNumberIndexShoes()
            checkNumberIndexCar()

            (context as MainActivity).runOnUiThread {
                item?.let {
                    when (product.title) {
                        "bottle" -> {
                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImage])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "car" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageCar])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "shoes" -> {
                            Glide.with(context)
                                .asBitmap()
                                .load(product.imageList[indexImageShoes])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        else -> Timber.d("rotateRight: Else branch")
                    }
                }
//                it.ivItem.loadImage(productList[0].imageList[indexImage])
            }
            delay(60)
        }
    }

    private suspend fun rotateLeft(count: Int, context: Context, item: View?, product: Product) {
        for (i in 0..count) {
            indexImage++
            indexImageShoes++
            indexImageCar++
            checkNumberIndex()
            checkNumberIndexShoes()
            checkNumberIndexCar()

            (context as MainActivity).runOnUiThread {
                item?.let {
                    when (product.title) {
                        "bottle" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImage])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "car" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageCar])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "shoes" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageShoes])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        else -> Timber.d("rotateLeft: Else branch")
                    }
                }
            }
            delay(60)
        }
    }

    private fun coroutinesStartFunction(context: Context, item: View?, product: Product) {
        GlobalScope.launch {
            playImageLikeGif(context, item, product)
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
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "car" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageCar])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        "shoes" -> {
                            Glide.with(context)
                                .load(product.imageList[indexImageShoes])
                                .placeholder(item.ivItem.drawable)
                                .into(item.ivItem)
                        }
                        else -> Timber.d("rotateLeft: Else branch")
                    }
//                it.ivItem.loadImage(productList[0].imageList[indexImage])
                }
            }
            delay(170)
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
}