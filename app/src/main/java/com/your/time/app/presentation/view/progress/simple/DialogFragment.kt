package com.your.time.app.presentation.view.progress.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.databinding.SimpleDialogFragmentBinding
import com.your.time.app.presentation.view.dialogs.BaseDialog

internal class DialogFragment : BaseDialog(0.3f, 0.3f) {

    private val binder: SimpleDialogFragmentBinding by lazy {
        SimpleDialogFragmentBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance() = DialogFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binder.root
    }
}