package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.events.EventBus
import com.eltonjhony.eventapp.infrastructure.events.WishListEvent
import com.eltonjhony.eventapp.infrastructure.extensions.drawable
import com.eltonjhony.eventapp.infrastructure.extensions.pump
import kotlinx.android.synthetic.main.favorite_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var event: Event

    init {
        View.inflate(context, R.layout.favorite_view, this)
    }

    fun setup(event: Event?) {
        event?.let {
            this.event = it
            checkFavoriteIcon()
            favoriteIcon.setOnClickListener {
                this.event.isFavorite = !this.event.isFavorite
                animateFavoriteIcon()
            }
        }
    }

    private fun animateFavoriteIcon() {
        GlobalScope.launch {
            EventBus.send(WishListEvent(event))
        }
        checkFavoriteIcon()
        favoriteIcon.pump()
    }

    private fun checkFavoriteIcon() {
        if (event.isFavorite) {
            favoriteIcon.setImageDrawable(context.drawable(R.drawable.ic_favorite))
        } else {
            favoriteIcon.setImageDrawable(context.drawable(R.drawable.ic_favorite_border))
        }
    }

}