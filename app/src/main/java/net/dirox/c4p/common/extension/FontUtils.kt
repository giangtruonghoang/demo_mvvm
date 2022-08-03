package net.dirox.c4p.common.extension

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import java.util.*

class FontUtils(private val mContext: Context?) {

    fun getTypeFace(font: String): Typeface? {
        var tempTypeface = sTypeFaces[font]

        if (tempTypeface == null) {
            tempTypeface = Typeface.createFromAsset(mContext!!.assets, font)
            sTypeFaces[font] = tempTypeface
        }

        return tempTypeface
    }

    fun setFont(fontName: String, view: View?) {
        if (mContext != null && view != null) {
            if (view is TextView)
                view.typeface = getTypeFace(fontName)

            if (view is Button)
                view.typeface = getTypeFace(fontName)

            if (view is EditText)
                view.typeface = getTypeFace(fontName)

            if (view is CheckBox)
                view.typeface = getTypeFace(fontName)
        }
    }

    companion object {

        var FONT_TYPE_LATO_REGULAR = "fonts/lato-regular.ttf"
        var FRUTIGER_LIGHT_FONT = "fonts/Frutiger45-Light.ttf"
        var FRUTIGER_ROMAN_FONT = "fonts/Frutiger55Roman.ttf"
        var FRUTIGER_BOLD_FONT = "fonts/Frutiger65-Bold.ttf"

        private val sTypeFaces = Hashtable<String, Typeface>(
            5
        )
    }


}