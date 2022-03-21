package com.example.product360view.presentation.productDetail

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.product360view.R
import com.example.product360view.common.extenstions.loadImageType
import com.example.product360view.domain.image.ImageType
import com.example.product360view.marcinmoskala.arcseekbar.ProgressListener
import kotlinx.android.synthetic.main.layout_product_detail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Rotate360SeekBarView : FrameLayout {

    private val TAG = Rotate360SeekBarView::class.java.simpleName
    private var productImageView: ImageView? = null
    private var productSeekBar: View? = null // ?????????
    private var productImageList: ArrayList<ImageType> = arrayListOf()

    constructor(context: Context) : super(context) {
        initView(null, 0)
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        initView(attr, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        //init attribute
        initView(attrs, defStyleAttr)
    }

    /**
     * init View Here
     */
    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.layout_product_detail, this, true)
        productImageView = rootView.findViewById(R.id.ivProductImage)
        productSeekBar = rootView.findViewById(R.id.sbImgRotation)

        val attrArray =
            context.obtainStyledAttributes(attrs, R.styleable.Rotate360SeekBarView, defStyleAttr, 0)
        attrArray.recycle()
    }

    private fun initProgressBarListener() {
        GlobalScope.launch {

            sbImgRotation.onProgressChangedListener = object : ProgressListener {
                override fun invoke(progress: Int) {
                    Log.d(TAG, "invoke: $progress")
                    if (progress < productImageList.size) {
                        productImageView?.loadImageType(productImageList[progress])
                    }
                }
            }

            //For Gradient Progressbar
            val intArray = resources.getIntArray(R.array.progressGradientColors)
            sbImgRotation.setProgressGradient(intArray)
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
            sbImgRotation.maxProgress = progressBarMax
        }
        initProgressBarListener()
    }
}