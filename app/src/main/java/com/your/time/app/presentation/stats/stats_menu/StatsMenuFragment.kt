package com.your.time.app.presentation.stats.stats_menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.databinding.FragmentStatsMenuBinding

class StatsMenuFragment : Fragment() {

    companion object {
        fun newInstance(): StatsMenuFragment = StatsMenuFragment()
    }

    private lateinit var binder: FragmentStatsMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = FragmentStatsMenuBinding.inflate(inflater)

        binder.cardActivityStats.setOnClickListener {
            onClickShowStatsListener?.onClickShowActivityStats()
        }

        binder.cardUsefulnessStats.setOnClickListener {
            onClickShowStatsListener?.onClickShowUsefulnessStats()
        }

        return binder.root
    }


    var onClickShowStatsListener: OnClickShowStatsListener? = null
    interface OnClickShowStatsListener {
        fun onClickShowActivityStats()
        fun onClickShowUsefulnessStats()
    }

}
