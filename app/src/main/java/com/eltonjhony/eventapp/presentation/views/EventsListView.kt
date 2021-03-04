package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.idling.CountingIdlingResource
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.extensions.hide
import com.eltonjhony.eventapp.infrastructure.extensions.show
import com.eltonjhony.eventapp.presentation.common.EndlessRecyclerViewScrollListener
import com.eltonjhony.eventapp.presentation.features.listevents.EventsAdapter
import kotlinx.android.synthetic.main.events_list_view.view.*

class EventsListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val eventsAdapter = EventsAdapter {
        onTapListener?.invoke(it)
    }

    private var onTapListener: ((Event) -> Unit)? = null

    private var endlessScrollListener: EndlessRecyclerViewScrollListener? = null

    init {
        View.inflate(context, R.layout.events_list_view, this)
    }

    fun setup(onTapListener: (Event) -> Unit) {
        this.onTapListener = onTapListener
        val linearLayoutManager = LinearLayoutManager(
            context
        )
        eventsRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = eventsAdapter
        }
        swipeRefreshLayout.isEnabled = false
    }

    fun onRefresh(refreshListener: () -> Unit) {
        swipeRefreshLayout.isEnabled = true
        swipeRefreshLayout.setOnRefreshListener {
            if (eventsAdapter.currentFilter.isNotEmpty()) {
                swipeRefreshLayout.isRefreshing = false
                return@setOnRefreshListener
            }
            refreshListener.invoke().also {
                swipeRefreshLayout.isRefreshing = true
                reset()
            }
        }
    }

    fun reset() {
        eventsAdapter.clear()
        endlessScrollListener?.resetState()
    }

    fun stopLoading() {
        if (!countingIdlingResource.isIdleNow) countingIdlingResource.decrement()
        swipeRefreshLayout.isRefreshing = false
        progressBar.hide()
    }

    fun startLoading() {
        countingIdlingResource.increment()
        if (swipeRefreshLayout.isRefreshing) progressBar.hide() else progressBar.show()
    }

    fun onLoadMore(loadListener: (nextPage: Int) -> Unit) {
        val layoutManager = eventsRecyclerView.layoutManager as LinearLayoutManager
        endlessScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (eventsAdapter.currentFilter.isEmpty()) {
                    val nextPage = currentPage + 1
                    setCurrentPage(nextPage)
                    loadListener(nextPage)
                }
            }
        }

        eventsRecyclerView.apply {
            endlessScrollListener?.let { addOnScrollListener(it) }
        }
    }

    fun filterBy(query: String) {
        eventsAdapter.filter.filter(query)
    }

    fun updateEmptyState() {
        if (eventsAdapter.isEmpty())
            showEmptyStateError()
    }

    fun updateResults(data: List<Event>?) {
        show()
        if (endlessScrollListener?.isFirstPage == true)
            eventsAdapter.setEvents(data)
        else
            eventsAdapter.updateEvents(data)
    }

    fun updateFavorite(event: Event, shouldDelete: Boolean = false) {
        if (event.isFavorite)
            eventsAdapter.didAddToWishList(event)
        else
            eventsAdapter.didRemoveFromWishList(event, shouldDelete)
    }

    fun showNetworkingError(tryAgainListener: () -> Unit) {
        val errorTitle = resources.getString(R.string.connection_error_title)
        val errorDescription = resources.getString(R.string.connection_error_description)
        errorView.showError(errorTitle, errorDescription, tryAgainListener)
        hide()
    }

    fun showDataNotFoundError() {
        errorView.showError(
            resources.getString(R.string.data_not_found_error_title),
            resources.getString(R.string.data_not_found_error_description)
        )
        hide()
    }

    fun showEmptyStateError() {
        errorView.showError(
            resources.getString(R.string.empty_wish_list_error_title),
            resources.getString(R.string.empty_wish_list_error_description)
        )
        hide()
    }

    fun showGenericError() {
        val errorTitle = resources.getString(R.string.connection_error_title)
        val errorDescription = resources.getString(R.string.error_generic_description)
        errorView.showError(errorTitle, errorDescription)
        hide()
    }

    private fun show() {
        errorView.hide()
        swipeRefreshLayout.show()
    }

    private fun hide() {
        errorView.show()
        swipeRefreshLayout.hide()
    }

    companion object {
        val countingIdlingResource = CountingIdlingResource("countingIdlingResource")
    }

}