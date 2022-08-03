package net.dirox.c4p.common.extension

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity

@SuppressLint("PrivateApi")
fun AppCompatActivity.setMiuiStatusBarDarkMode(activity: Activity, darkmode: Boolean): Boolean {
    val clazz = activity.window.javaClass
    try {
        var darkModeFlag = 0
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField =
            clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        extraFlagField.invoke(activity.window, if (darkmode) darkModeFlag else 0, darkModeFlag)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}