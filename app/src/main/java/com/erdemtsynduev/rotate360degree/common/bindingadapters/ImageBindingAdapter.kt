package com.erdemtsynduev.rotate360degree.common.bindingadapters

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.erdemtsynduev.rotate360degree.R

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: AppCompatImageView, url: String?) {
        if (url?.isNotEmpty() == true) {
            Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.ic_car_product)
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: AppCompatImageView, url: Drawable?) {
        imageView.setImageDrawable(url)
    }
}