package com.nedaluof.qurany.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Created by nedaluof on 1/31/2021.
 */
@ExperimentalCoroutinesApi
fun Context.connectivityFlow() = callbackFlow {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Create Callback
    val callback = NetworkCallback { connectionState ->
        offer(connectionState)
    }

    // Register Callback
    connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), callback)

    // Set current state
    val currentState = getCurrentConnectivityState(connectivityManager)
    offer(currentState)

    // Remove callback when not used
    awaitClose {
        // Remove listeners
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

private fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
    var currentState = ConnectionState.NOT_CONNECTED

    // Retrieve current status of connectivity
    connectivityManager.allNetworks.forEach { network ->
        val networkCapability = connectivityManager.getNetworkCapabilities(network)

        networkCapability?.let {
            if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                currentState = ConnectionState.CONNECTED
                return@forEach
            }
        }
    }
    return currentState
}

@Suppress("FunctionName")
fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.CONNECTED)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.NOT_CONNECTED)
        }
    }
}

enum class ConnectionState {
    CONNECTED, NOT_CONNECTED
}
