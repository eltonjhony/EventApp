package com.eltonjhony.eventapp.infrastructure.extensions

import com.eltonjhony.eventapp.infrastructure.logging.Logger
import java.text.SimpleDateFormat
import java.util.*

fun String.parse(pattern: String, locale: Locale = Locale.getDefault()): Date? =
    runCatching {
        SimpleDateFormat(pattern, locale).parse(this)
    }.getOrElse {
        Logger.withTag(String::class.java.simpleName).d(it.localizedMessage)
        null
    }