package net.dirox.c4p.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import net.dirox.c4p.R

class LoadingDialog(
    context: Context
) : Dialog(context, false, null) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
        window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}