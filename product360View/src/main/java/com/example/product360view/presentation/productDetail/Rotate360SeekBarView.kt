package com.example.product360view.presentation.productDetail

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
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
    //...............

    /**
     * Used to scale the dp units to pixels
     */
    protected val DPTOPX_SCALE = resources.displayMetrics.density

    /**
     * Minimum touch target size in DP. 48dp is the Android design recommendation
     */
    protected val MIN_TOUCH_TARGET_DP = 48f

    /**
     * `Paint` instance used to draw the inactive circle.
     */
    protected var mCirclePaint: Paint? = null

    /**
     * `Paint` instance used to draw the circle fill.
     */
    protected var mCircleFillPaint: Paint? = null

    /**
     * `Paint` instance used to draw the active circle (represents progress).
     */
    protected var mCircleProgressPaint: Paint? = null

    /**
     * `Paint` instance used to draw the glow from the active circle.
     */
    protected var mCircleProgressGlowPaint: Paint? = null

    /**
     * `Paint` instance used to draw the center of the pointer.
     * Note: This is broken on 4.0+, as BlurMasks do not work with hardware acceleration.
     */
    protected var mPointerPaint: Paint? = null

    /**
     * `Paint` instance used to draw the halo of the pointer.
     * Note: The halo is the part that changes transparency.
     */
    protected var mPointerHaloPaint: Paint? = null

    /**
     * `Paint` instance used to draw the border of the pointer, outside of the halo.
     */
    protected var mPointerHaloBorderPaint: Paint? = null

    /**
     * The width of the circle (in pixels).
     */
    protected var mCircleStrokeWidth = 0f

    /**
     * The X radius of the circle (in pixels).
     */
    protected var mCircleXRadius = 0f

    /**
     * The Y radius of the circle (in pixels).
     */
    protected var mCircleYRadius = 0f

    /**
     * The radius of the pointer (in pixels).
     */
    protected var mPointerRadius = 0f

    /**
     * The width of the pointer halo (in pixels).
     */
    protected var mPointerHaloWidth = 0f

    /**
     * The width of the pointer halo border (in pixels).
     */
    protected var mPointerHaloBorderWidth = 0f

    /**
     * Start angle of the CircularSeekBar.
     * Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted
     * from the mEndAngle to make the circle function properly.
     */
    protected var mStartAngle = 0f

    /**
     * End angle of the CircularSeekBar.
     * Note: If mStartAngle and mEndAngle are set to the same angle, 0.1 is subtracted
     * from the mEndAngle to make the circle function properly.
     */
    protected var mEndAngle = 0f

    /**
     * `RectF` that represents the circle (or ellipse) of the seekbar.
     */
    protected var mCircleRectF = RectF()

    /**
     * Holds the color value for `mPointerPaint` before the `Paint` instance is created.
     */
    protected var mPointerColor: Int =
        DEFAULT_POINTER_COLOR

    /**
     * Holds the color value for `mPointerHaloPaint` before the `Paint` instance is created.
     */
    protected var mPointerHaloColor: Int =
        DEFAULT_POINTER_HALO_COLOR

    /**
     * Holds the color value for `mPointerHaloPaint` before the `Paint` instance is created.
     */
    protected var mPointerHaloColorOnTouch: Int =
        DEFAULT_POINTER_HALO_COLOR_ONTOUCH

    /**
     * Holds the color value for `mCirclePaint` before the `Paint` instance is created.
     */
    protected var mCircleColor: Int =
        DEFAULT_CIRCLE_COLOR

    /**
     * Holds the color value for `mCircleFillPaint` before the `Paint` instance is created.
     */
    protected var mCircleFillColor: Int =
        DEFAULT_CIRCLE_FILL_COLOR

    /**
     * Holds the color value for `mCircleProgressPaint` before the `Paint` instance is created.
     */
    protected var mCircleProgressColor: Int =
        DEFAULT_CIRCLE_PROGRESS_COLOR

    /**
     * Holds the alpha value for `mPointerHaloPaint`.
     */
    protected var mPointerAlpha: Int =
        DEFAULT_POINTER_ALPHA

    /**
     * Holds the OnTouch alpha value for `mPointerHaloPaint`.
     */
    protected var mPointerAlphaOnTouch: Int =
        DEFAULT_POINTER_ALPHA_ONTOUCH

    /**
     * Distance (in degrees) that the the circle/semi-circle makes up.
     * This amount represents the max of the circle in degrees.
     */
    protected var mTotalCircleDegrees = 0f

    /**
     * Distance (in degrees) that the current progress makes up in the circle.
     */
    protected var mProgressDegrees = 0f

    /**
     * `Path` used to draw the circle/semi-circle.
     */
    protected var mCirclePath: Path? = null

    /**
     * `Path` used to draw the progress on the circle.
     */
    protected var mCircleProgressPath: Path? = null

    /**
     * Max value that this CircularSeekBar is representing.
     */
    protected var mMax = 0

    /**
     * Progress value that this CircularSeekBar is representing.
     */
    protected var mProgress = 0

    /**
     * If true, then the user can specify the X and Y radii.
     * If false, then the View itself determines the size of the CircularSeekBar.
     */
    protected var mCustomRadii = false

    /**
     * Maintain a perfect circle (equal x and y radius), regardless of view or custom attributes.
     * The smaller of the two radii will always be used in this case.
     * The default is to be a circle and not an ellipse, due to the behavior of the ellipse.
     */
    protected var mMaintainEqualCircle = false

    /**
     * Once a user has touched the circle, this determines if moving outside the circle is able
     * to change the position of the pointer (and in turn, the progress).
     */
    protected var mMoveOutsideCircle = false
    /**
     * Get whether the pointer locks at zero and max.
     *
     * @return Boolean value of true if the pointer locks at zero and max, false if it does not.
     */
    /**
     * Set whether the pointer locks at zero and max or not.
     *
     * @param boolean value. True if the pointer should lock at zero and max, false if it should not.
     */
    /**
     * Used for enabling/disabling the lock option for easier hitting of the 0 progress mark.
     */
    var isLockEnabled = true

    protected fun init(attrs: AttributeSet?, defStyle: Int) {
        val attrArray =
            context.obtainStyledAttributes(attrs, R.styleable.Rotate360SeekBarView, defStyle, 0)
        initAttributes(attrArray)
        attrArray.recycle()
    }

    protected fun initAttributes(attrArray: TypedArray) {
        mCircleXRadius = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_circle_x_radius,
            DEFAULT_CIRCLE_X_RADIUS * DPTOPX_SCALE
        )
        mCircleYRadius = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_circle_y_radius,
            DEFAULT_CIRCLE_Y_RADIUS * DPTOPX_SCALE
        )
        mPointerRadius = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_pointer_radius,
            DEFAULT_POINTER_RADIUS * DPTOPX_SCALE
        )
        mPointerHaloWidth = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_pointer_halo_width,
            DEFAULT_POINTER_HALO_WIDTH * DPTOPX_SCALE
        )
        mPointerHaloBorderWidth = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_pointer_halo_border_width,
            DEFAULT_POINTER_HALO_BORDER_WIDTH * DPTOPX_SCALE
        )
        mCircleStrokeWidth = attrArray.getDimension(
            R.styleable.Rotate360SeekBarView_circle_stroke_width,
            DEFAULT_CIRCLE_STROKE_WIDTH * DPTOPX_SCALE
        )
        mPointerColor = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_pointer_color,
            DEFAULT_POINTER_COLOR
        )
        mPointerHaloColor = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_pointer_halo_color,
            DEFAULT_POINTER_HALO_COLOR
        )
        mPointerHaloColorOnTouch = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_pointer_halo_color_ontouch,
            DEFAULT_POINTER_HALO_COLOR_ONTOUCH
        )
        mCircleColor = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_circle_color,
            DEFAULT_CIRCLE_COLOR
        )
        mCircleProgressColor = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_circle_progress_color,
            DEFAULT_CIRCLE_PROGRESS_COLOR
        )
        mCircleFillColor = attrArray.getColor(
            R.styleable.Rotate360SeekBarView_circle_fill,
            DEFAULT_CIRCLE_FILL_COLOR
        )
        Log.d(TAG, "initAttributes: mCircleFillColor 290 => $mCircleFillColor")
        mPointerAlpha = Color.alpha(mPointerHaloColor)
        mPointerAlphaOnTouch = attrArray.getInt(
            R.styleable.Rotate360SeekBarView_pointer_alpha_ontouch,
            DEFAULT_POINTER_ALPHA_ONTOUCH
        )
        if (mPointerAlphaOnTouch > 255 || mPointerAlphaOnTouch < 0) {
            mPointerAlphaOnTouch =
                DEFAULT_POINTER_ALPHA_ONTOUCH
        }
        mMax = attrArray.getInt(
            R.styleable.Rotate360SeekBarView_max,
            DEFAULT_MAX
        )
        mProgress = attrArray.getInt(
            R.styleable.Rotate360SeekBarView_progress,
            DEFAULT_PROGRESS
        )
        mCustomRadii = attrArray.getBoolean(
            R.styleable.Rotate360SeekBarView_use_custom_radii,
            DEFAULT_USE_CUSTOM_RADII
        )
        mMaintainEqualCircle = attrArray.getBoolean(
            R.styleable.Rotate360SeekBarView_maintain_equal_circle,
            DEFAULT_MAINTAIN_EQUAL_CIRCLE
        )
        mMoveOutsideCircle = attrArray.getBoolean(
            R.styleable.Rotate360SeekBarView_move_outside_circle,
            DEFAULT_MOVE_OUTSIDE_CIRCLE
        )
        isLockEnabled = attrArray.getBoolean(
            R.styleable.Rotate360SeekBarView_lock_enabled,
            DEFAULT_LOCK_ENABLED
        )

        // Modulo 360 right now to avoid constant conversion
        mStartAngle = (360f + attrArray.getFloat(
            R.styleable.Rotate360SeekBarView_start_angle,
            DEFAULT_START_ANGLE
        ) % 360f) % 360f
        mEndAngle = (360f + attrArray.getFloat(
            R.styleable.Rotate360SeekBarView_end_angle,
            DEFAULT_END_ANGLE
        ) % 360f) % 360f
        if (mStartAngle == mEndAngle) {
            //mStartAngle = mStartAngle + 1f;
            mEndAngle = mEndAngle - .1f
        }
    }


    //...............

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
        initAttributes(attrArray)
        attrArray.recycle()

        val mSeekbar = rootView.findViewById<Rotate360SeekBar>(R.id.sbImgRotation)
        initParentAttribute(mSeekbar)
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
//                    Log.d(TAG, "onProgressChanged: PROGRESS => $progress")
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
//            sbImgRotation.onProgressChangedListener(mProgress)

/*            val progressListener = ProgressListener { progress ->
                Log.i("SeekBar", "Value is $progress")
                Toast.makeText(context, "Marcin progress => $progress", Toast.LENGTH_SHORT).show()
            }
            progressListener.invoke(0)*/
//            sbImgRotation.onProgressChangedListener()
            /*sbImgRotation?.let {
                it.onProgressChangedListener(progressListener)
            }*/

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

    private fun initParentAttribute(mSeekbar: Rotate360SeekBar) {
        mSeekbar.circleColor = mCircleColor
        mSeekbar.circleProgressColor = mCircleProgressColor
        mSeekbar.pointerColor = mPointerColor
        mSeekbar.pointerHaloColor = mPointerHaloColor
        mSeekbar.pointerAlpha = mPointerAlpha
        mSeekbar.pointerAlphaOnTouch = mPointerAlphaOnTouch
        mSeekbar.circleFillColor = mCircleFillColor
        mSeekbar.max = mMax
        mSeekbar.progress = mProgress
        mSeekbar.startAngle = mStartAngle
        mSeekbar.endAngle = mEndAngle
        mSeekbar.moveOutsideCircle = mMoveOutsideCircle
        mSeekbar.maintainEqualCircle = mMaintainEqualCircle
        mSeekbar.customRadii = mCustomRadii
        mSeekbar.isLockEnabled = isLockEnabled
        mSeekbar.isLockEnabled = isLockEnabled
        mSeekbar.circleXRadius = mCircleXRadius
        mSeekbar.circleYRadius = mCircleYRadius
        mSeekbar.circleStrokeWidth = mCircleStrokeWidth
        mSeekbar.pointerRadius = mPointerRadius
        mSeekbar.pointerHaloWidth = mPointerHaloWidth
        mSeekbar.pointerHaloBorderWidth = mPointerHaloBorderWidth
    }

    companion object {
        // Default values
        protected const val DEFAULT_CIRCLE_X_RADIUS = 30f
        protected const val DEFAULT_CIRCLE_Y_RADIUS = 30f
        protected const val DEFAULT_POINTER_RADIUS = 7f
        protected const val DEFAULT_POINTER_HALO_WIDTH = 6f
        protected const val DEFAULT_POINTER_HALO_BORDER_WIDTH = 2f
        protected const val DEFAULT_CIRCLE_STROKE_WIDTH = 5f
        protected const val DEFAULT_START_ANGLE =
            270f // Geometric (clockwise, relative to 3 o'clock)
        protected const val DEFAULT_END_ANGLE =
            270f // Geometric (clockwise, relative to 3 o'clock)
        protected const val DEFAULT_MAX = 100
        protected const val DEFAULT_PROGRESS = 0
        protected const val DEFAULT_CIRCLE_COLOR = Color.DKGRAY
        protected val DEFAULT_CIRCLE_PROGRESS_COLOR = Color.argb(235, 74, 138, 255)
        protected val DEFAULT_POINTER_COLOR = Color.argb(235, 74, 138, 255)
        protected val DEFAULT_POINTER_HALO_COLOR = Color.argb(135, 74, 138, 255)
        protected val DEFAULT_POINTER_HALO_COLOR_ONTOUCH = Color.argb(135, 74, 138, 255)
        protected const val DEFAULT_CIRCLE_FILL_COLOR = Color.TRANSPARENT
        protected const val DEFAULT_POINTER_ALPHA = 135
        protected const val DEFAULT_POINTER_ALPHA_ONTOUCH = 100
        protected const val DEFAULT_USE_CUSTOM_RADII = false
        protected const val DEFAULT_MAINTAIN_EQUAL_CIRCLE = true
        protected const val DEFAULT_MOVE_OUTSIDE_CIRCLE = false
        protected const val DEFAULT_LOCK_ENABLED = true
    }
}