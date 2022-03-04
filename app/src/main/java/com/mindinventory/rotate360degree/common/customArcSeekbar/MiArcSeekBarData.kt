package com.mindinventory.rotate360degree.common.customArcSeekbar

import android.graphics.RectF

internal data class MiArcSeekBarData(
    val dx: Float,//69.5
    val dy: Float,//69.5
    val width: Float,//785
    val height: Float,//71
    val progress: Int,//0
    val maxProgress: Int//36
) {
    private val pi = Math.PI.toFloat()
    private val zero = 0.0001F
    val r: Float = height / 2 + width * width / 8 / height // 1120.4
    private val circleCenterX: Float = width / 2 + dy//circleCenterX 462, dy 69.5, width 785
    private val circleCenterY: Float = r + dx //circleCenterY 11890, r 1120.4,  dx 69.5
    private val alphaRad: Float = bound(0F, Math.acos((r - height).toDouble() / r).toFloat(), 2 * pi)//alphaRad 0.35791308,   // static value changes the position of thumb
    val arcRect: RectF = RectF(circleCenterX - r, circleCenterY - r, circleCenterX + r, circleCenterY + r)  //To set size of Arc
//    val arcRect: RectF = RectF(circleCenterX - r, -2310.3064F, circleCenterX + r, circleCenterY - r)//RectF(-658.4032, 2310.3064, 1582.4032, 69.5)
//    val arcRect: RectF = RectF(5F, 5F, 600F, 550F)//RectF(-658.4032, 2310.3064, 1582.4032, 69.5)
//    val startAngle: Float = bound(180F, 270 - alphaRad / 2 / pi * 360F, 360F)//249.49
    val startAngle: Float = 0F//249.49 //Keeping start angle to 0 will show lower arc
//    val sweepAngle: Float = bound(zero, (2F * alphaRad) / 2 / pi * 360F, 180F)//41.01
    val sweepAngle: Float = 0F//41.01
//    val progressSweepRad = if(maxProgress == 0) zero else bound(zero, progress.toFloat() / maxProgress * 2 * alphaRad, 2 * pi)//1.0E-4
    val progressSweepRad = if(maxProgress == 0) zero else bound(zero, progress.toFloat() / maxProgress * 2 * alphaRad, 2 * pi)//1.0E-4
//    val progressSweepAngle: Float = progressSweepRad / pi * 360F//Half circle progress will be drawn by keeping /2    //0.0052
    val progressSweepAngle: Float = progressSweepRad / pi * 180F//Half circle progress will be drawn by keeping /2    //0.0052
    val thumbX: Int = (r * Math.cos(alphaRad + Math.PI / 2 - progressSweepRad).toFloat() + circleCenterX).toInt()//69
    val thumbY: Int = (-r * Math.sin(alphaRad + Math.PI / 2 - progressSweepRad).toFloat() + circleCenterY).toInt()//140

    fun progressFromClick(x: Float, y: Float, thumbHeight: Int): Int? {
        if (y > height + dy * 2) return null
        val distToCircleCenter = Math.sqrt(Math.pow(circleCenterX - x.toDouble(), 2.0) + Math.pow(circleCenterY - y.toDouble(), 2.0))
        if (Math.abs(distToCircleCenter - r) > thumbHeight) return null
        val innerWidthHalf = width / 2
        val xFromCenter = bound(-innerWidthHalf, x - circleCenterX, innerWidthHalf).toDouble()
        val touchAngle = Math.acos(xFromCenter / r) + alphaRad - Math.PI / 2
        val angleToMax = 1.0 - touchAngle / (2 * alphaRad)
        return bound(0, ((maxProgress + 1) * angleToMax).toInt(), maxProgress)
    }
}
