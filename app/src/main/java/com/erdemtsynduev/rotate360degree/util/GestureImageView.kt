package com.erdemtsynduev.rotate360degree.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.erdemtsynduev.rotate360degree.util.MyLog.d

class GestureImageView : androidx.appcompat.widget.AppCompatButton {
    var mContext: Context

    interface OnOverLayerTouchDownListener {
        fun onOverLayerTouchDown()
    }

    interface OnOverLayerTouchUpListener {
        fun onOverLayerTouchUp()
    }

    interface OnOverLayerTouchMoveListener {
        fun onOverLayerTouchMove(x: Float, y: Float)
    }

    var mTouchDownListener: OnOverLayerTouchDownListener? = null
    var mTouchUpListener: OnOverLayerTouchUpListener? = null
    var mTouchMoveListener: OnOverLayerTouchMoveListener? = null

    fun setOverLayerTouchDownListener(l: OnOverLayerTouchDownListener?) {
        mTouchDownListener = l
    }

    fun setOverLayerTouchUpListener(l: OnOverLayerTouchUpListener?) {
        mTouchUpListener = l
    }

    fun setOverLayerTouchMoveListener(l: OnOverLayerTouchMoveListener?) {
        mTouchMoveListener = l
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled) {
            processEvent(event) // 私下处理event
            /**
             * 可以在这里判断MOVE的距离
             * 如果超出阀值，可以停止传播，使得下层的View得不到event
             * 可以考虑添加回调函数，把这个逻辑放在GestureButtonShow.java里面
             */
            super.dispatchTouchEvent(event) // 继续传播event
            return true // 本层也继续接收event
        }
        return super.dispatchTouchEvent(event)
    }

    private fun processEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event)
            MotionEvent.ACTION_MOVE -> touchMove(event)
            MotionEvent.ACTION_UP -> touchUp(event)
            MotionEvent.ACTION_CANCEL -> {
            }
        }
        return false
    }

    private fun touchDown(event: MotionEvent) {
        d(MyLog.DEBUG, "touchDown()!")
        mTouchDownListener!!.onOverLayerTouchDown()
    }

    private fun touchMove(event: MotionEvent) {
        d(MyLog.DEBUG, "touchMove()")
        mTouchMoveListener!!.onOverLayerTouchMove(event.x, event.y)
    }

    private fun touchUp(event: MotionEvent) {
        d(MyLog.DEBUG, "touchUp()!")
        mTouchUpListener!!.onOverLayerTouchUp()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        d(MyLog.DEBUG, "GestureButtonLayout(3)")
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        d(MyLog.DEBUG, "GestureButtonLayout(2)")
        mContext = context
    }

    constructor(context: Context) : super(context) {
        d(MyLog.DEBUG, "GestureButtonLayout(1)")
        mContext = context
    }
}
