package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.infrastructure.extensions.invisible
import com.eltonjhony.eventapp.infrastructure.extensions.show
import kotlinx.android.synthetic.main.toolbar_view.view.*

class ToolbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            titleTextView.text = title
        }

    var goBackAction: (() -> (Unit))? = null
        set(value) {
            field = value
            setupBackAction()
        }

    var wishListAction: (() -> (Unit))? = null
        set(value) {
            field = value
            setupWishListAction()
        }

    var filterAction: (() -> (Unit))? = null
        set(value) {
            field = value
            setupFilterAction()
        }

    var currentQuery = ""
        private set

    var searchBarAction: ((String) -> (Unit))? = null
        set(value) {
            field = value
            setupSearchBarAction()
        }

    private fun setupWishListAction() {
        wishListMenuItem.setup(
            context.getString(R.string.wish_list_label),
            R.drawable.ic_favorite_menu
        )
        wishListMenuItem.show()
        wishListMenuItem.setOnClickListener {
            wishListAction?.invoke()
        }
    }

    private fun setupFilterAction() {
        filterMenuItem.setup(
            context.getString(R.string.filter_label),
            R.drawable.ic_filter_list_icon
        )
        filterMenuItem.show()
        filterMenuItem.setOnClickListener {
            filterAction?.invoke()
        }
    }

    private fun setupBackAction() {
        goBackIcon.show()
        goBackIcon.setOnClickListener {
            goBackAction?.invoke()
        }
    }

    private fun setupSearchBarAction() {
        searchView.show()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    currentQuery = newText
                    searchBarAction?.invoke(it)
                }
                return false
            }
        })
    }

    init {
        View.inflate(context, R.layout.toolbar_view, this)
        goBackIcon.invisible()
        wishListMenuItem.invisible()
        searchView.invisible()
        filterMenuItem.invisible()
    }

}