package com.example.product360view.presentation.productDetail

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.product360view.R
import com.example.product360view.common.extenstions.loadImageType
import com.example.product360view.domain.image.ImageType
import com.example.product360view.marcinmoskala.arcseekbar.ArcSeekBar
import com.example.product360view.marcinmoskala.arcseekbar.ProgressListener
import com.example.product360view.marcinmoskala.arcseekbar.bound
import kotlinx.android.synthetic.main.layout_product_detail.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Rotate360SeekBarView : FrameLayout {

    private var mRoundedEdges: Boolean = false
    private var mEnabled: Boolean = false
    private var mProgressBackgroundColor: Int = 0
    private var mProgressPaint: Int = 0
    private var mProgressBackgroundWidth: Float = 0f
    private var mProgress: Int = 0
    private var mProgressWidth: Float = 0f
    private var mMaxProgress: Int = 0
    private lateinit var mThumb: Drawable


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

        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.Rotate360SeekBarView, 0, 0)
        applyAttribute(attrArray)
        initParentAttribute(productSeekBar as ArcSeekBar?)
    }

    private fun initParentAttribute(productSeekBar: ArcSeekBar?) {
        productSeekBar?.apply {
            thumb = mThumb
            maxProgress = mMaxProgress
            progress = mProgress
            progressWidth = mProgressWidth
            progressBackgroundWidth = mProgressBackgroundWidth
            progressColor = mProgressPaint
            progressBackgroundColor = mProgressBackgroundColor
            isEnabled = mEnabled
            roundedEdges = mRoundedEdges
        }
    }

    private fun applyAttribute(attrArray: TypedArray) {
        try {
            mThumb = attrArray.getDrawable(R.styleable.Rotate360SeekBarView_thumb) ?: resources.getDrawable(R.drawable.thumb)
            mMaxProgress = attrArray.useOrDefault(100) { getInteger(R.styleable.ArcSeekBar_parent_maxProgress, it)}
            mProgress = attrArray.useOrDefault(0) { getInteger(R.styleable.ArcSeekBar_parent_progress, it) }
            mProgressWidth = attrArray.useOrDefault(4 * context.resources.displayMetrics.density) { getDimension(R.styleable.ArcSeekBar_parent_progressWidth, it) }
            mProgressBackgroundWidth = attrArray.useOrDefault(2F) { getDimension(R.styleable.ArcSeekBar_parent_progressBackgroundWidth, it) }
            mProgressPaint = attrArray.useOrDefault(ContextCompat.getColor(context, android.R.color.holo_blue_light)) { getColor(R.styleable.ArcSeekBar_parent_progressColor, it) }
            mProgressBackgroundColor = attrArray.useOrDefault(ContextCompat.getColor(context, android.R.color.darker_gray)) { getColor(R.styleable.ArcSeekBar_parent_progressBackgroundColor, it) }
            mEnabled = attrArray.getBoolean(R.styleable.ArcSeekBar_parent_enabled, true) ?: true
            mRoundedEdges = attrArray.useOrDefault(true) { getBoolean(R.styleable.ArcSeekBar_parent_roundEdges, it) }

            progressGradientId = attrArray.getResourceId(R.styleable.ArcSeekBar_parent_progressGradient, 0)
            Log.d(TAG, "progressGradientId: $progressGradientId")
            if (progressGradientId != 0) {
                progressGradientArray = resources.getIntArray(progressGradientId)
            }

/*            //Got user passed array
            val progressBackgroundWidth = attrArray.getResourceId(R.styleable.ArcSeekBar_parent_progressBackgroundWidth, 0)
            Log.d(TAG, "applyAttribute: progressBackgroundWidth => $progressBackgroundWidth")
            
            progressGradientId = attrArray.getResourceId(R.styleable.ArcSeekBar_parent_progressGradient, 0)

            Log.d(TAG, "progressGradientId: $progressGradientId")
            if (progressGradientId != 0) {
                progressGradientArray = resources.getIntArray(progressGradientId)
            }
            Log.d(TAG, "progressGradientArray: $progressGradientArray")
//            sbImgRotation.setProgressGradient(progressGradientArray)*/
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
//            sbImgRotation.maxProgress = progressBarMax
            sbImgRotation.setProgressGradient(progressGradientArray)
            Log.d(TAG, "setUpProgressBar: $progressGradientId")
            Log.d(TAG, "setUpProgressBar: $progressGradientArray")
        }
        initProgressBarListener()
    }
    fun <T, R> T?.useOrDefault(default: R, usage: T.(R) -> R) = if (this == null) default else usage(default)
}