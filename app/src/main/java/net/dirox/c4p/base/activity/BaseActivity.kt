package net.dirox.c4p.base.activity

import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.reactivex.disposables.CompositeDisposable
import net.dirox.c4p.R
import net.dirox.c4p.common.LocalNotification
import net.dirox.c4p.common.dialog.LoadingDialog
import net.dirox.c4p.common.extension.FontUtils
import net.dirox.c4p.common.extension.setMiuiStatusBarDarkMode

abstract class BaseActivity: ConnectivityChangeActivity() {

    protected var waitingForNetwork: Boolean = false

    private var progress: LoadingDialog? = null

    val disposeBag: CompositeDisposable by lazy {
         CompositeDisposable()
    }
    protected var mAlertDialog: Dialog? = null
    lateinit var mFontUtils: FontUtils

    companion object {
        var needShowCreateAccount: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMiuiStatusBarDarkMode(this, true)

        if(resources.getBoolean(R.bool.portrait_only)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onStart() {
        super.onStart()
        mFontUtils = FontUtils(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(mTokenExpiredReceiver, IntentFilter(LocalNotification.RefreshTokenFailed))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mTokenExpiredReceiver)
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
        progress?.dismiss()
        progress = null
    }

    fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        view.clearFocus()
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        editText.setSelection(editText.text.length)
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun showLoading() {
        if (progress != null && progress!!.isShowing){
            hideLoading()
        }
        progress = LoadingDialog(this)
        progress?.show()
    }

    fun hideLoading() {
        progress?.hide()
    }

    private val mTokenExpiredReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }

    override fun onConnectedToNetwork() {
        super.onConnectedToNetwork()

        if (waitingForNetwork) {
            waitingForNetwork = false
            retry()
        }
    }

    protected open fun retry() {
        // Retry if needed. Only work when waitingForNetwork = true
    }
}