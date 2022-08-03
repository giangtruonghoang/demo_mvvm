package net.dirox.c4p.common.extension


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import net.dirox.c4p.R


fun AppCompatActivity.back() {
    this.finish()
}

fun AppCompatActivity.changeStatusBarColor(resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window ?: return
        window.statusBarColor = ContextCompat.getColor(this, resId)
    }
}

fun AppCompatActivity.changeStatusBarColorRed() {
    changeStatusBarColor(R.color.colorRed1)
}

fun AppCompatActivity.changeStatusBarColorWhite() {
    changeStatusBarColor(R.color.white)
}


