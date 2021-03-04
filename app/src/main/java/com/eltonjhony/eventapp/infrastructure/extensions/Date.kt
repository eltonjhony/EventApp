package com.eltonjhony.eventapp.infrastructure.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatWith(pattern: String, locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat(pattern, locale).format(this)

fun Date.dayOfWeek(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK)
}