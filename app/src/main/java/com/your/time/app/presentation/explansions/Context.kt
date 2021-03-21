package com.your.time.app.presentation.explansions

import android.content.Context
import android.os.Build
import android.support.annotation.ColorRes
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.your.time.app.R

fun Context.showSimpleMessage(message: String){

    val okText = getString(R.string.ok_caps)

    val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setPositiveButton(okText, null)
            .setMessage(message)
            .create()
    dialog.show()

    val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    okButton.setOnClickListener {
        dialog.dismiss()
    }
}

fun Context.shortToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(text: String){
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

@Suppress("DEPRECATION")
fun Context.color(@ColorRes colorId: Int): Int{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(colorId, theme)
    else resources.getColor(colorId)
}