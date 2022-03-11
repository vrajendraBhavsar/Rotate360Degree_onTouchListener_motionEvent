package com.example.product360view.presentation.productDetail

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.product360view.R
import com.example.product360view.common.extenstions.loadImageType
import com.example.product360view.common.rotate360SeekBarSeekbar.Rotate360SeekBar
import com.example.product360view.domain.image.ImageType
import kotlinx.android.synthetic.main.layout_product_detail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Rotate360SeekBarView : FrameLayout {
    private val TAG = Rotate360SeekBarView::class.java.simpleName

    private var productImageView: ImageView? = null
    private var productSeekBar: View? = null // ?????????

    private var productImageList: ArrayList<ImageType> = arrayListOf()

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
        productImageView = rootView.findViewById(R.id.ivProductImage)
        productSeekBar = rootView.findViewById(R.id.sbImgRotation)
    }

    private fun initProgressBarListener() {
        GlobalScope.launch {
            /*productSeekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Log.d(TAG, "onProgressChanged: PROGRESS => $progress")
                    if (progress < productImageList.size) {
                        productImageView?.loadImageType(productImageList[progress])
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })*/
            class Rotate360SeekBarListener : Rotate360SeekBar.OnCircularSeekBarChangeListener {
                override fun onProgressChanged(
                    rotate360SeekBar: Rotate360SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Log.d(TAG, "onProgressChanged: PROGRESS => $progress")
                    if (progress < productImageList.size) {
                        productImageView?.loadImageType(productImageList[progress])
                    }
                }

                override fun onStopTrackingTouch(seekBar: Rotate360SeekBar) {

                }

                override fun onStartTrackingTouch(seekBar: Rotate360SeekBar) {

                }
            }
            sbImgRotation.setOnSeekBarChangeListener(Rotate360SeekBarListener())
        }
    }

    /**
     * to get list of images here in the custom view
     **/
    fun setImageList(imageList: ArrayList<ImageType>) {
        Log.d(TAG, "setImageList: $imageList")
        if (imageList.size <= 0) {
            throw IllegalArgumentException("Product Image list is empty.")
        } else {
            this.productImageList.apply {
                clear()
                addAll(imageList)
            }
            productImageView?.loadImageType(this.productImageList[0])
            setUpProgressBar(progressBarMax = this.productImageList.size)
        }
    }

    private fun setUpProgressBar(progressBarMax: Int) {
        productSeekBar?.apply {
            Log.d(TAG, "setUpProgressBar: $progressBarMax")
            sbImgRotation.max = progressBarMax
        }
        initProgressBarListener()
    }
}