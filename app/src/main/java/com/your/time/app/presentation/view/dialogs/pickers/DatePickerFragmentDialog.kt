package com.your.time.app.presentation.view.dialogs.pickers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.databinding.FragmentDatePickerFragmentDialogBinding
import com.your.time.app.presentation.view.dialogs.BaseDialog
import java.util.*

class DatePickerFragmentDialog : BaseDialog(0.9f, 0.9f) {

    var minTime: Long? = null
    var maxTime: Long? = null
    var selectedTime: Long? = null

    companion object {

        private const val MIN_TIME = "minTime"
        private const val MAX_TIME = "maxTime"
        private const val SELECTED_TIME = "selectedTime"

        fun newInstance(minTime: Long? = null, maxTime: Long? = null, selectedTime: Long? = null, onDataSelectedListener: OnDataSelectedListener? = null): DatePickerFragmentDialog {
            val bundle = Bundle()
            minTime?.let { bundle.putLong(MIN_TIME, minTime) }
            maxTime?.let { bundle.putLong(MAX_TIME, maxTime) }
            selectedTime?.let { bundle.putLong(SELECTED_TIME, selectedTime) }
            val dialog = DatePickerFragmentDialog()
            dialog.arguments = bundle
            dialog.onDataSelectedListener = onDataSelectedListener
            return dialog
        }
    }

    var onDataSelectedListener: OnDataSelectedListener? = null
    interface OnDataSelectedListener {
        fun onDataSelected(year: Int, month: Int, dayOfMonth: Int)
    }

    private val binder: FragmentDatePickerFragmentDialogBinding by lazy {
        FragmentDatePickerFragmentDialogBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fun getData(){
            minTime = arguments?.getLong(MIN_TIME)
            maxTime = arguments?.getLong(MAX_TIME)
            selectedTime = arguments?.getLong(SELECTED_TIME)
        }

        fun initPicker(){

            minTime?.let {
                binder.datePicker.minDate = minTime!!
            }

            maxTime?.let {
                binder.datePicker.maxDate = maxTime!!
            }

            selectedTime?.let {
                val selectedData = Calendar.getInstance()
                selectedData.timeInMillis = selectedTime!!

                val year = selectedData[Calendar.YEAR]
                val month = selectedData[Calendar.MONTH]
                val dayOfMonth = selectedData[Calendar.DAY_OF_MONTH]
                binder.datePicker.updateDate(year, month, dayOfMonth)
            }
        }

        fun initOK(){
            binder.ok.setOnClickListener {

                val year = binder.datePicker.year
                val month = binder.datePicker.month
                val dayOfMonth = binder.datePicker.dayOfMonth

                onDataSelectedListener?.onDataSelected(year, month, dayOfMonth)

                dismiss()
            }
        }

        getData()
        initPicker()
        initOK()

        return binder.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

}
