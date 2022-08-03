package net.dirox.c4p.base.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import net.dirox.c4p.common.util.Utils

abstract class ConnectivityChangeActivity : AppCompatActivity() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            runOnUiThread {
                onConnectedToNetwork()
            }
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
            runOnUiThread {
                onLostConnection()
            }
        }
    }

    private val connectivityManager by lazy { getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    override fun onResume() {
        super.onResume()
        val networkRequestBuilder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), networkCallback)
        if (!Utils.isNetworkAvailable(this)) {
            onLostConnection()
        }
    }

    override fun onPause() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onPause()
    }

    protected open fun onConnectedToNetwork() {
    }

    protected open fun onLostConnection() {
    }
}