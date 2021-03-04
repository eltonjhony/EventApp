package com.eltonjhony.eventapp.presentation.features.eventdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.extensions.load
import com.eltonjhony.eventapp.presentation.common.BaseActivity
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.content_event_details_layout.*

class EventDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_event_details)
        super.onCreate(savedInstanceState)

        setupUI()
    }

    override fun setupToolbar() {
        toolbarView.title = getString(R.string.event_details_label)
        toolbarView.goBackAction = { onBackPressed() }
    }

    private fun setupUI() {

        val event = intent.getParcelableExtra<Event>(EVENT_EXTRA_KEY)

        carouselView.pageCount = event?.getBanners()?.size ?: 0
        carouselView.setImageListener { position, imageView ->
            imageView.load(event?.getBanners()?.get(position))
        }

        eventNameTextView.text = event?.name
        dateAndTimeText.text = event?.getDateAndTime() ?: getString(R.string.to_be_defined_label)
        locationView.setup(event?.location)
        favoriteView.setup(event)
    }

    companion object {
        private const val EVENT_EXTRA_KEY = "EVENT_EXTRA_PARAM"

        fun callingIntent(context: Context, event: Event) {
            context.startActivity(Intent(context, EventDetailsActivity::class.java).apply {
                putExtra(EVENT_EXTRA_KEY, event)
            })
        }
    }
}