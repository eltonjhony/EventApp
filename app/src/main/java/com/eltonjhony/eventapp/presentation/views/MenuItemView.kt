package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.infrastructure.extensions.drawable
import com.eltonjhony.eventapp.infrastructure.extensions.rotate
import kotlinx.android.synthetic.main.menu_item_view.view.*

class MenuItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.menu_item_view, this)
    }

    fun setup(text: String, @DrawableRes icon: Int) {
        iconImageView.setImageDrawable(context.drawable(icon))
        menuLabel.text = text
    }

    fun updateWishList(qty: Int?) {
        wishListNumber.text = "$qty"
        qty?.let {
            if (it > 0) {
                iconImageView.rotate()
            }
        }
    }

}