package net.dirox.c4p.common.extension

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder
import net.dirox.c4p.R

fun RequestBuilder<Drawable>.custom(): RequestBuilder<Drawable> {
    return error(R.drawable.ic_photo_placeholder)
}