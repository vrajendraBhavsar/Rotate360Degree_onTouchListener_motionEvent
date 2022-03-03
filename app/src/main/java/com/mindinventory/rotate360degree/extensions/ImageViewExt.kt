package com.mindinventory.rotate360degree.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mindinventory.rotate360degree.R
import java.io.File

fun ImageView?.loadCenterCropDrawable(url: String?) {
    this ?: return
    url ?: return

    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(
    path: String, isCircle: Boolean = false, isCenterCrop: Boolean = false
) {

    if (!path.isRequiredField())
        return

    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontTransform()

    val url: String = if (path.isUrl()) {
        path
    } else {
        Uri.fromFile(File(path)).toString()
    }

    var isGif = false

    if (url.isRequiredField()) {
        val extension: String = url.substring(url.lastIndexOf(".")).toLowerCase()
        isGif = extension.contains(".gif")
    }

    if (isGif) {

        val requestOptions = RequestOptions()
            .transform(RoundedCorners(20))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(context)
            .asGif()
            .load(url)
            .apply(requestOptions)
            .into(this)

    } else {
        val result = Glide.with(context)
            .load(url)
            .error(R.color.white)
            .apply(options)

        /*result = when {
            isCircle -> {
                val bitmap = BitmapFactory.decodeResource(context.resources, placeholder)
                val circularBitmapDrawable: RoundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.resources, bitmap)
                circularBitmapDrawable.isCircular = true
                result.apply(
                        RequestOptions.circleCropTransform().placeholder(circularBitmapDrawable)
                )
            }
            isCenterCrop -> {
                result.centerCrop()

            }
            else -> {
                result.fitCenter()
            }
        }*/
        result.into(this)
    }
}