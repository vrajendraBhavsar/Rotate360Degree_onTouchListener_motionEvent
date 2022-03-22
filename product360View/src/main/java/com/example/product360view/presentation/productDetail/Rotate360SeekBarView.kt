package com.example.product360view.presentation.productDetail

import android.content.Context
import android.content.res.TypedArray
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

    private var trackGradientArray: IntArray = context.resources.getIntArray(R.array.trackGradientColors)
    private var progressGradientArray: IntArray = context.resources.getIntArray(R.array.progressGradientColors)
    private var progressGradientId: Int = 0

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

        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar, 0, 0)
//        resources.obtainTypedArray(R.styleable.ArcSeekBar)
        applyAttribute(attrArray)
    }

    private fun applyAttribute(attrArray: TypedArray) {
        try {
            //Got user passed array
            val progressBackgroundWidth = attrArray.getResourceId(R.styleable.ArcSeekBar_progressBackgroundWidth, 0)
            Log.d(TAG, "applyAttribute: progressBackgroundWidth => $progressBackgroundWidth")
            
            progressGradientId = attrArray.getResourceId(R.styleable.ArcSeekBar_progressGradient, 0)

            Log.d(TAG, "progressGradientId: $progressGradientId")
            if (progressGradientId != 0) {
                progressGradientArray = resources.getIntArray(progressGradientId)
            }
            Log.d(TAG, "progressGradientArray: $progressGradientArray")
//            sbImgRotation.setProgressGradient(progressGradientArray)
        } finally {
            attrArray.recycle()
        }
    }

    private fun initProgressBarListener() {
        GlobalScope.launch {

            sbImgRotation.onProgressChangedListener = object : ProgressListener {
                override fun invoke(progress: Int) {
                    if (progress < productImageList.size) {
                        productImageView?.loadImageType(productImageList[progress])
                    }
                }
            }

            //For Gradient Progressbar
//            val intArray = resources.getIntArray(R.array.progressGradientColors)
//            sbImgRotation.setProgressGradient(progressGradientArray)
        }
    }

//    fun setProgressGradientArray(progressGradientArray : IntArray){
//        this.progressGradientArray = progressGradientArray
//
//        invalidate()
//    }

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
            sbImgRotation.maxProgress = progressBarMax
            sbImgRotation.setProgressGradient(progressGradientArray)
            Log.d(TAG, "setUpProgressBar: $progressGradientId")
            Log.d(TAG, "setUpProgressBar: $progressGradientArray")
        }
        initProgressBarListener()
    }
}