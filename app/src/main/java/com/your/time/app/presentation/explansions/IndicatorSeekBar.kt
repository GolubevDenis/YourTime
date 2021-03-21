package com.your.time.app.presentation.explansions

import com.warkiz.widget.IndicatorSeekBar
import io.reactivex.Observable

fun IndicatorSeekBar.changes(): Observable<Int>{
    return Observable.create<Int> {
        this.setOnSeekChangeListener(object : IndicatorSeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?, thumbPosOnTick: Int) {}
            override fun onSectionChanged(seekBar: IndicatorSeekBar?, thumbPosOnTick: Int, textBelowTick: String?, fromUserTouch: Boolean) {}
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {}
            override fun onProgressChanged(seekBar: IndicatorSeekBar?, progress: Int, progressFloat: Float, fromUserTouch: Boolean) {
                it.onNext(progress)
            }
        })
    }
}