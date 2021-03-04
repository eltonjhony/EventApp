package com.eltonjhony.eventapp.presentation.common

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.domain.Genre
import com.eltonjhony.eventapp.infrastructure.EventObserver
import com.eltonjhony.eventapp.infrastructure.Resource
import com.eltonjhony.eventapp.infrastructure.ResourceState.Success
import com.eltonjhony.eventapp.infrastructure.events.EventBus
import com.eltonjhony.eventapp.infrastructure.events.WishListEvent
import com.eltonjhony.eventapp.infrastructure.extensions.dpToPx
import com.eltonjhony.eventapp.infrastructure.logging.Logger
import com.eltonjhony.eventapp.presentation.features.wishlist.WishListViewModel
import com.eltonjhony.eventapp.presentation.views.FilterCheckItemView
import kotlinx.android.synthetic.main.activity_list_events.*
import kotlinx.android.synthetic.main.activity_wish_list.*
import kotlinx.android.synthetic.main.event_filter_dialog.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_view.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    protected val wishListViewModel: WishListViewModel by viewModel()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        launch { subscribeEvents() }
        wishListViewModel.countResource.observe(
            this,
            EventObserver { bindWishListCounterState(it) })

        wishListViewModel.getWishListRowCount()
    }

    override fun onStop() {
        super.onStop()
        coroutineContext.cancelChildren()
    }

    open fun bindWishListCounterState(resource: Resource<Int>?) {
        resource?.run {
            if (state is Success) {
                toolbarView?.wishListMenuItem?.updateWishList(data)
            }
        }
    }

    abstract fun setupToolbar()

    private suspend fun subscribeEvents() {
        EventBus.on<WishListEvent>().collect {
            wishListViewModel.persistWishList(it.event)
            withContext(Dispatchers.Main) {
                eventsListView?.updateFavorite(it.event)
                wishListView?.updateFavorite(it.event, true)
            }
        }
    }
}

fun BaseActivity.showFilterDialog(
    genres: List<Genre> = emptyList(),
    onCloseCallback: (() -> Unit)? = null
) {

    runCatching {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.event_filter_dialog)

            genres.map {
                val filter = FilterCheckItemView(context)
                filter.option = it
                optionsContent.addView(filter)
            }

            window?.attributes = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = context.dpToPx(350f)
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.CENTER
            }

            closeButton.setOnClickListener {
                dismiss()
                onCloseCallback?.let { it() }
            }

        }.show()
    }.onFailure {
        Logger.withTag(Activity::class.java.simpleName).withCause(it)
    }

}