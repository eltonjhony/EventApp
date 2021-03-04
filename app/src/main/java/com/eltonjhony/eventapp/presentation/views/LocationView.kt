package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.EventLocation
import kotlinx.android.synthetic.main.location_view.view.*
import java.util.*

class LocationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.location_view, this)
    }

    fun setup(location: EventLocation?) {
        location?.let {
            eventLocationTextView.text = it.toString()
            gpsButton.setOnClickListener {
                val uri: String = java.lang.String.format(
                    Locale.getDefault(),
                    "http://maps.google.com/maps?q=loc:%s,%s",
                    location.latitude,
                    location.longitude
                )
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                context.startActivity(intent)
            }

        }
    }

}