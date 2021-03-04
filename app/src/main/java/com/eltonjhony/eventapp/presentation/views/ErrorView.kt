package com.eltonjhony.eventapp.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.eltonjhony.eventapp.R
import com.eltonjhony.eventapp.infrastructure.extensions.hide
import com.eltonjhony.eventapp.infrastructure.extensions.show
import kotlinx.android.synthetic.main.error_view.view.*

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.error_view, this)
    }

    fun showError(
        errorTitle: String,
        errorDescription: String,
        tryAgainListener: (() -> Unit)? = null
    ) {
        errorTitleTextView.text = errorTitle
        errorDescriptionTextView.text = errorDescription
        loadingView.hide()
        tryAgainListener?.let { listener ->
            tryAgainButton.setOnClickListener {
                loadingView.show()
                listener.invoke()
            }
            tryAgainButton.show()
        }
        show()
    }

}