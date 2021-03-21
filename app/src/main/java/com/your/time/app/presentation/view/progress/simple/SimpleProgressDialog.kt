package com.your.time.app.presentation.view.progress.simple

import android.support.v7.app.AppCompatActivity
import com.your.time.app.presentation.view.progress.ProgressDialog

class SimpleProgressDialog (
        private val activity: AppCompatActivity
) : ProgressDialog {

    private var dialogFragment: DialogFragment? = null

    private companion object {
        const val TAG = "SimpleProgressDialog"
    }

    override fun show() {
        dialogFragment?.dismiss()
        dialogFragment = DialogFragment.newInstance()
        dialogFragment!!.show(activity.supportFragmentManager, TAG)
    }

    override fun hide() {
        dialogFragment?.dismiss()
        dialogFragment = null
    }
}