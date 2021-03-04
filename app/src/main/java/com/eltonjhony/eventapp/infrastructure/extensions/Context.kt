package com.eltonjhony.eventapp.infrastructure.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.drawable(@DrawableRes id: Int): Drawable? = runCatching {
    ContextCompat.getDrawable(this, id)
}.getOrNull()

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()