package com.eltonjhony.eventapp.infrastructure.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.eltonjhony.eventapp.infrastructure.logging.Logger

fun <T> Activity.goTo(
    destination: Class<T>,
    bundle: Bundle? = null
) where T : Activity {
    runCatching {
        val intent = Intent(this.applicationContext, destination)
        bundle?.let { intent.putExtras(it) }
        startActivity(intent)

    }.onFailure {
        Logger.withTag(Activity::class.java.simpleName).withCause(it)
    }
}