package com.example.product360view.common.extenstions

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.product360view.domain.image.ImageType

fun ImageView.loadImageType(imageType: ImageType) {
    when (imageType) {
        is ImageType.ResourceId -> {
            Glide.with(context)
                .load(imageType.imageRes)
                .error(this.drawable)
                .placeholder(this.drawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
        }

        is ImageType.Asset -> {
            Glide.with(context)
                .asBitmap()
                .load(imageType.imageString)
                .placeholder(this.drawable)
                .into(this)
        }

        is ImageType.BitMap -> {
            Glide.with(context)
                .asBitmap()
                .load(imageType.imageString)
                .error(this.drawable)
                .placeholder(this.drawable)
                .into(this)
        }

        is ImageType.BitMapRemoteImage -> {
            Glide.with(context)
                .asBitmap()
                .load(imageType.imageUrl)
                .error(this.drawable)
                .placeholder(this.drawable)
                .sizeMultiplier(0.4f)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        this@loadImageType.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                })
        }
    }
}

