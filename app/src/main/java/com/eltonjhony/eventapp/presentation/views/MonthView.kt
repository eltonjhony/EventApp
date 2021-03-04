package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import kotlinx.android.synthetic.main.month_view.view.*

class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var month: String? = null
        set(value) {
            field = value
            setupUI()
        }

    init {
        View.inflate(context, R.layout.month_view, this)
    }

    private fun setupUI() {
        monthTextView.text = month ?: context.getString(R.string.to_be_defined_label)
    }

}