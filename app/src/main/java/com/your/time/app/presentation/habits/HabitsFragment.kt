package com.your.time.app.presentation.habits


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.your.time.app.MyApplication
import com.your.time.app.databinding.FragmentHabitsBinding
import com.your.time.app.di.presentation.habits.DaggerHabitsComponent
import com.your.time.app.di.presentation.habits.HabitsComponent
import com.your.time.app.domain.model.HabitModel
import com.your.time.app.presentation.habits.adapter.HabitHolder
import com.your.time.app.presentation.habits.adapter.HabitsAdapter
import com.your.time.app.presentation.habits.mvp.HabitsPresenter
import com.your.time.app.presentation.habits.mvp.HabitsView
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.hannesdorfmann.mosby3.mvp.MvpFragment


class HabitsFragment : MvpFragment<HabitsView, HabitsPresenter>(), HabitsView {

    private val component: HabitsComponent by lazy {
        DaggerHabitsComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }
    private val listHabits = ListForAdapterBase<HabitModel, HabitHolder>()
    private val adapter: HabitsAdapter by lazy { HabitsAdapter(listHabits, layoutInflater, component.getTimeUtil()) }

    override fun createPresenter() = component.getPresenter()

    private val binder: FragmentHabitsBinding by lazy {
        FragmentHabitsBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(onClickAddHabitListener: OnClickAddHabitListener) =
                HabitsFragment().also {
                    it.onClickAddHabitListener =  onClickAddHabitListener
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun initCreate(){
            binder.addBtn.setOnClickListener {
                onClickAddHabitListener?.onClickAddHabit()
            }
        }

        fun initList(){
            binder.list.layoutManager = GridLayoutManager(context, 2)
            binder.list.adapter = adapter
        }

        initCreate()
        initList()

        return binder.root
    }

    override fun showHabits(habits: List<HabitModel>) {
        listHabits.setItems(habits)
    }

    override fun showError() {
        // TODO
    }

    private var onClickAddHabitListener: OnClickAddHabitListener? = null
    interface OnClickAddHabitListener {
        fun onClickAddHabit()
    }

}
