package com.example.product360view.presentation.productDetail

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import com.example.product360view.R
import com.example.product360view.common.extenstions.loadImageType
import com.example.product360view.domain.image.ImageType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomSeekbarView : FrameLayout {
    private val TAG = CustomSeekbarView::class.java.simpleName

    var productImageList: ArrayList<ImageType> = arrayListOf()

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView()
    }

    /**
     * init View Here
     */
    private fun initView() {
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.layout_product_detail, this, true)

//        val rootView = (context
//            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//            .inflate(R.layout.layout_product_detail, this, true)

//        gravity = VERTICAL

        // Load and use rest of views here
        val imageView = rootView.findViewById<ImageView>(R.id.ivProductImage)
        val seekBar = rootView.findViewById<SeekBar>(R.id.sbImgRotation)

        Log.d(TAG, "initView: HASTALAVISTA $productImageList")

//        if (productImageList.isNullOrEmpty()) {
        GlobalScope.launch {
            seekBar.max = productImageList.size
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
//                        Toast.makeText(requireContext(), "discrete seekbar progress: $progress", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onProgressChanged: HASTALAVISTA progress 66: $progress")
                    if (progress > 0 && progress < productImageList.size) {
//                        if (productImageList.isNullOrEmpty()) {
                        imageView.loadImageType(productImageList[progress])
//                            imageView.loadImageType(ImageType.Asset("file:///android_asset/car/4.png"))
                        Log.d(TAG, "onProgressChanged: HASTALAVISTA productImageList is not null")
                    }
                    //...

                    /*context?.let {
                        Glide.with(it)
                            .asBitmap()
                            .load("file:///android_asset/car/4.png")
                            .placeholder(imageView.drawable)
                            .into(imageView)
                    }*/

//                        } else {
//                            Log.d(TAG, "onProgressChanged: HASTALAVISTA productImageList IS NULL")
//                        }
//                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
//            }
        }
    }

    /**
     * to get list of images here in the custom view
     **/
    fun setImageList(imageList: ArrayList<ImageType>) {
        Log.d(TAG, " setImageList: HASTALAVISTA $imageList")
        productImageList = imageList
        Log.d(TAG, "setImageList: HASTALAVISTA $productImageList")
    }

    object DataProvider {
        fun getCarImageList(): ArrayList<ImageType> {
            return ArrayList<ImageType>().apply {
                //Car - example
                for (i in 52 downTo 1) {
                    add(ImageType.Asset(imageString = "file:///android_asset/car/${i}.png"))
                }
            }

//            return ArrayList<Product>().apply {
//                add(Product(title = "car", imageList = ArrayList<String>().apply {
//                    /*Taking images from the assert folder*/
//                    for (i in 52 downTo 1) {
//                        add("file:///android_asset/car/${i}.png")
//                    }
//                }, description = R.string.text_car_description))
//                add(Product(title = "bottle", imageList = ArrayList<String>().apply {
//                    for (i in 2696..2731) {
//                        add("file:///android_asset/bottle/AVF_${i}.jpg")
//                    }
//                }, description =R.string.text_bottle_description))
//                add(Product(title = "shoes", imageList = ArrayList<String>().apply {
//                    for (i in 1..18) {
//                        add("file:///android_asset/shoes/image1_${i}.jpg")
//                    }
//                }, description = R.string.text_shoes_description))
//            }
        }
    }

}