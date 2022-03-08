package com.example.product360view.common.extenstions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.product360view.domain.common.image.ImageConfig

fun ImageView.loadImage(source: String?) {
    Glide.with(context)
        .load(source ?: "")
        .error(ImageConfig.getConfig().errorDrawable)
        .placeholder(ImageConfig.getConfig().placeHolder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun ImageView.loadBitmapImage(bitmapSource: String?) {
    Glide.with(context)
        .asBitmap()
        .load(bitmapSource ?: "")
        .error(ImageConfig.getConfig().errorDrawable)
        .placeholder(ImageConfig.getConfig().placeHolder)
        .sizeMultiplier(0.4f)
        .into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                this@loadBitmapImage.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })
}

fun ImageView.loadImageFromResourceId(resourceId: String?) {
    Glide.with(context)
        .load(resourceId?.let { getImage(it, this) })
        .error(ImageConfig.getConfig().errorDrawable)
        .placeholder(ImageConfig.getConfig().placeHolder)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun getImage(drawableImageName: String, imageView: ImageView): Int {
    val drawableResourceId: Int = imageView.resources.getIdentifier(
        drawableImageName,
        "drawable",
        imageView.context.packageName
    )
    return drawableResourceId
}

