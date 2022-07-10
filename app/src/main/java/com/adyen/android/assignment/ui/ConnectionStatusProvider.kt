package com.adyen.android.assignment.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class ConnectionStatusProvider(
    private val context: Context,
) {
    private var onAvailable: () -> Unit = {}
    private var onLost: () -> Unit = {}
    var isConnected = true
        private set

    fun registerAdditionalCallback(onAvailable: () -> Unit, onLost: () -> Unit) {
        this.onAvailable = onAvailable
        this.onLost = onLost
    }

    private val networkCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                isConnected = true
                onAvailable.invoke()
            }

            override fun onLost(network: Network) {
                isConnected = false
                onLost.invoke()
            }
        }

    init {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        manager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .build(),
            networkCallback
        )
    }
}