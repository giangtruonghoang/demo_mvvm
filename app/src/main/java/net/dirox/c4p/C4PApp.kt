package net.dirox.c4p

import android.app.Application
import android.content.Intent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.branch.referral.Branch
import net.dirox.c4p.common.LocalNotification
import net.dirox.c4p.data.prefs.UserPrefs
import net.dirox.c4p.di.module.netModule
import net.dirox.c4p.di.module.repositoryModule
import net.dirox.c4p.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class C4PApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@C4PApp)
            modules(
                listOf(
                    repositoryModule,
                    netModule,
                    viewModelModule
                )
            )
        }

        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        // Branch logging for debugging
        Branch.enableDebugMode()

        // Initialize the Branch object
        Branch.getAutoInstance(this)
    }

    companion object {
        lateinit var instance: C4PApp
        const val INTENT_ACTION_APP_START = "APP_ON_START"
    }

    fun onAccessTokenRefreshed() {

    }

    fun onRefreshTokenFailed() {
        UserPrefs.clear()
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(LocalNotification.RefreshTokenFailed))
    }
}