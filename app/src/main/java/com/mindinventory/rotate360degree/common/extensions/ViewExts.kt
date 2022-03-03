package com.mindinventory.rotate360degree.common.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

fun View.invisible() {
    this.isInvisible = true
}

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}

fun View.showOnlyWhen(isShow: Boolean) {
    if (isShow)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

fun View.hideKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View?.showSnackBar(message: String?, icon: Int = 0) {
    this ?: return
    if (!message.isNullOrEmpty()) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
            show()
        }
    }
}

fun View?.showSnackBar(message: Int, icon: Int = 0) {
    this ?: return
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        show()
    }
}

/*fun AppCompatEditText?.showHidePassword(isShow: Boolean, image: AppCompatImageView) {
    if (isShow) {
        image.setImageResource(R.drawable.ic_show_password)
        this?.transformationMethod = HideReturnsTransformationMethod.getInstance()
    } else {
        image.setImageResource(R.drawable.ic_hide_password)
        this?.transformationMethod = PasswordTransformationMethod.getInstance()
    }
    this?.setSelection(this.text.toString().length)
}*/

fun View?.expand() {
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((this?.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    this.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = this.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    this.layoutParams?.height = 1
    this.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height =
                if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.duration = (targetHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(a)
}

fun View?.collapse() {
    val view = this
    val initialHeight = this?.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                view?.visibility = View.GONE
            } else {
                if (initialHeight != null) {
                    view?.layoutParams?.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                }
                view?.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    if (initialHeight != null) {
        a.duration = (initialHeight / view!!.context.resources.displayMetrics.density).toLong()
    }
    this?.startAnimation(a)
}

