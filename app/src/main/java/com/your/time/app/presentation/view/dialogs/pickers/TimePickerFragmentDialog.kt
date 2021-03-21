package com.your.time.app.presentation.view.dialogs.pickers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.databinding.FragmentTimePickerFragmentDialogBinding
import com.your.time.app.presentation.view.dialogs.BaseDialog

class TimePickerFragmentDialog : BaseDialog(0.9f, 0.9f) {

    var hours: Int? = null
    var minutes: Int? = null

    companion object {

        fun newInstance(onDataSelectedListener: OnTimeSelectedListener? = null): TimePickerFragmentDialog {
            val dialog = TimePickerFragmentDialog()
            dialog.onTimeSelectedListener = onDataSelectedListener
            return dialog
        }
    }

    var onTimeSelectedListener: OnTimeSelectedListener? = null
    interface OnTimeSelectedListener {
        fun onTimeSelected(hours: Int, minutes: Int)
    }

    private val binder: FragmentTimePickerFragmentDialogBinding by lazy {
        FragmentTimePickerFragmentDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fun initPicker(){
            binder.timePicker.setIs24HourView(true)

            binder.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
                this.hours = hourOfDay
                this.minutes = minute
            }
        }

        fun initOK(){
            binder.ok.setOnClickListener {
                if(hours != null && minutes != null){
                    onTimeSelectedListener?.onTimeSelected(hours!!, minutes!!)
                    dismiss()
                }
            }
        }

        initPicker()
        initOK()

        return binder.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}