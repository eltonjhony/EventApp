package com.eltonjhony.eventapp.presentation.features.listevents

import android.os.Bundle
import androidx.lifecycle.Observer
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.domain.Genre
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResourceState.*
import com.eltonjhony.eventapp.infrastructure.extensions.goTo
import com.eltonjhony.eventapp.presentation.common.BaseActivity
import com.eltonjhony.eventapp.presentation.common.showFilterDialog
import com.eltonjhony.eventapp.presentation.features.eventdetails.EventDetailsActivity.Companion.callingIntent
import com.eltonjhony.eventapp.presentation.features.wishlist.WishListActivity
import kotlinx.android.synthetic.main.activity_list_events.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListEventsActivity : BaseActivity() {

    private val viewModel: ListEventsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_list_events)
        super.onCreate(savedInstanceState)
        viewModel.eventsResource.observe(this, Observer { bindEventState(it) })

        setupUI()
        loadEvents()
    }

    override fun setupToolbar() {
        toolbarView.title = getString(R.string.upcoming_events_label)
        toolbarView.wishListAction = {
            goTo(WishListActivity::class.java)
        }
        toolbarView.searchBarAction = { query ->
            eventsListView.filterBy(query)
        }
        toolbarView.filterAction = {
            showFilterDialog(Genre.getDefault()) {
                eventsListView.reset()
                loadEvents()
            }
        }
    }

    private fun setupUI() {
        eventsListView.setup {
            callingIntent(this, it)
        }

        eventsListView.onRefresh {
            loadEvents()
        }

        eventsListView.onLoadMore { nextPage ->
            loadEvents(nextPage)
        }
    }

    private fun loadEvents(page: Int = 1) =
        viewModel.fetchEvents(page)

    private fun bindEventState(resource: Resource<List<Event>>?) {
        resource?.run {
            if (state != Loading) {
                eventsListView.stopLoading()
            }
            when (state) {
                Loading -> eventsListView.startLoading()
                Success -> eventsListView.updateResults(data)
                GenericError -> eventsListView.showGenericError()
                NetworkError -> eventsListView.showNetworkingError { loadEvents() }
                DataNotFoundError -> eventsListView.showDataNotFoundError()
            }
        }
    }

}