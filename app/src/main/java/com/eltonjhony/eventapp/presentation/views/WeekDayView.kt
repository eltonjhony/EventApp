package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import kotlinx.android.synthetic.main.week_day_view.view.*

class WeekDayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.week_day_view, this)
    }

    fun setup(day: Int?, dayOfWeek: String?) {
        eventDayNumberTextView.text = ""
        eventDayTextView.text = ""
        day?.let {
            eventDayNumberTextView.text = "$it"
            eventDayTextView.text = dayOfWeek
        }
    }

}