package com.your.time.app.presentation.view.dialogs

import android.support.v4.app.DialogFragment
import android.view.Gravity
import com.your.time.app.presentation.explansions.getHeightDisplay
import com.your.time.app.presentation.explansions.getWidthDisplay

abstract class BaseDialog : DialogFragment {

    private val widthProportion: Float
    private val heightProportion: Float

    constructor(widthProportion: Float = 0.8f, heightProportion: Float = 0.8f){
        this.widthProportion = widthProportion
        this.heightProportion = heightProportion
    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        val displayWidth = activity!!.getWidthDisplay()
        val displayHeight = activity!!.getHeightDisplay()
        window!!.setLayout((displayWidth * widthProportion).toInt(), (displayHeight * heightProportion).toInt())
        window.setGravity(Gravity.CENTER)
    }
}