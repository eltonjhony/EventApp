package com.eltonjhony.eventapp.presentation.features.wishlist

import android.os.Bundle
import androidx.lifecycle.Observer
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Event
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResourceState.Success
import com.eltonjhony.eventapp.presentation.common.BaseActivity
import com.eltonjhony.eventapp.presentation.features.eventdetails.EventDetailsActivity.Companion.callingIntent
import kotlinx.android.synthetic.main.activity_wish_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*

class WishListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_wish_list)
        super.onCreate(savedInstanceState)
        setupUI()

        wishListViewModel.wishListResource.observe(this, Observer { bindWishListState(it) })
        wishListViewModel.fetchWishList()
    }

    override fun setupToolbar() {
        toolbarView.title = getString(R.string.my_wish_list_label)
        toolbarView.goBackAction = { onBackPressed() }
    }

    override fun bindWishListCounterState(resource: Resource<Int>?) {
        super.bindWishListCounterState(resource)
        wishListView.updateEmptyState()
    }

    private fun setupUI() {
        wishListView.setup { callingIntent(this, it) }
    }

    private fun bindWishListState(resource: Resource<List<Event>>?) {
        resource?.run {
            when (state) {
                Success -> wishListView.updateResults(data)
                else -> wishListView.showEmptyStateError()
            }
        }
    }

}