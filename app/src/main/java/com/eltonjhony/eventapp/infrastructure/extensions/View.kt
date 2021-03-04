package com.eltonjhony.eventapp.infrastructure.extensions

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorite_view.view.*

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.roundBorders(curveRadius: Int = 4) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0,
                0,
                width,
                height,
                curveRadius.toFloat()
            )
        }
    }
    clipToOutline = true
}

fun ImageView.load(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun View.pump(count: Int = 3) {
    val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
        favoriteIcon,
        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
        PropertyValuesHolder.ofFloat("scaleY", 1.2f)
    )
    scaleDown.duration = 300
    scaleDown.repeatCount = count
    scaleDown.repeatMode = ObjectAnimator.REVERSE
    scaleDown.start()
}

fun View.rotate(duration: Long = 1000) {
    val rotate =
        RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
    rotate.duration = duration
    rotate.interpolator = LinearInterpolator()
    startAnimation(rotate)
}