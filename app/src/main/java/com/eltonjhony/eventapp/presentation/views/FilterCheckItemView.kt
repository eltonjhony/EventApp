package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.data.local.GenreFilterLocalPreferences.addFilter
import com.eltonjhony.eventapp.data.local.GenreFilterLocalPreferences.find
import com.eltonjhony.eventapp.data.local.GenreFilterLocalPreferences.removeFilter
import com.eltonjhony.eventapp.domain.Genre
import kotlinx.android.synthetic.main.filter_check_item_view.view.*

class FilterCheckItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var option: Genre? = null
        set(value) {
            field = value
            setupElement()
        }

    init {
        View.inflate(context, R.layout.filter_check_item_view, this)
    }

    private fun setupElement() {
        checkOption.text = option?.name
        checkOption.isChecked = find(option)
        checkOption.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                addFilter(option)
            else
                removeFilter(option)
        }
    }

}