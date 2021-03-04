package com.eltonjhony.eventapp.data.remote

import android.content.Context
import com.eltonjhony.eventapp.infrastructure.extensions.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected ?: false
}