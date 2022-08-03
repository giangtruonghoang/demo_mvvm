package net.dirox.c4p.common.extension

import android.app.Activity
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import net.dirox.c4p.R

//Get String (string.xml) from ID String Response Error Message from Server
fun AppCompatActivity.getStringResourceByName(aString: String?): String {
    if (aString == null) {
        return getString(R.string.default_error)
    }
    val resId = resources.getIdentifier(aString.replace(".", "_").replace("-", "_"), "string", packageName)
    return if (resId != 0) {
         getString(resId)
    } else aString
}

