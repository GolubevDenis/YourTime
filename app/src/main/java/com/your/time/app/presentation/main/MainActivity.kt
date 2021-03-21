package com.your.time.app.presentation.main

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.your.time.app.MyApplication
import com.your.time.app.R
import com.your.time.app.databinding.ActivityMainBinding
import com.your.time.app.di.presentation.main.DaggerMainComponent
import com.your.time.app.di.presentation.main.MainComponent
import com.your.time.app.presentation.add_habit.AddHabitFragment
import com.your.time.app.presentation.add_task.AddTaskFragment
import com.your.time.app.presentation.explansions.itemClicks
import com.your.time.app.presentation.explansions.setFragment
import com.your.time.app.presentation.habits.HabitsFragment
import com.your.time.app.presentation.home.HomeFragment
import com.your.time.app.presentation.main.mvp.MainPresenter
import com.your.time.app.presentation.main.mvp.MainView
import com.your.time.app.presentation.mark.MarkFragment
import com.your.time.app.presentation.setting.SettingFragment
import com.your.time.app.presentation.stats.stats_usefulness.StatsUsefulnessFragment
import com.your.time.app.presentation.tasks.TasksFragment
import com.your.time.app.presentation.uploading_actions.UploadingActionsActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.your.time.app.presentation.stats.stats_activity.ActivityStatsFragment
import com.your.time.app.presentation.stats.stats_menu.StatsMenuFragment


class MainActivity : MvpActivity<MainView, MainPresenter>(),
        HomeFragment.OnMarkClickListener,
        MainView,
        TasksFragment.OnClickAddTaskListener,
        AddTaskFragment.OnTaskAddedListener,
        HabitsFragment.OnClickAddHabitListener, AddHabitFragment.OnHabitAddedListener {

    private val LAYOUT_ID = R.layout.activity_main

    private lateinit var binder: ActivityMainBinding
    private lateinit var component: MainComponent

    override fun createPresenter(): MainPresenter {
        component = DaggerMainComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
        return component.getPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT_ID)
        binder = DataBindingUtil.setContentView(this, LAYOUT_ID)

        fun initSettingButton() {
            binder.toolbar?.setting?.setOnClickListener {
                setSettingFragment()
            }
        }

        if (savedInstanceState == null)
            setHomeFragment()

        binder.navigation.itemClicks().subscribe {
            when (it.itemId) {
//                R.id.navigation_habits -> {
//                    setHabitsFragment()
//                }
//                R.id.navigation_goals -> {
//                    setTasksFragment()
//                }
                R.id.navigation_home -> {
                    setHomeFragment()
                }
                R.id.navigation_stats -> {
                    setStatsFragment()
                }
            }
        }

        // Select Home item
        selectHomeFragment()

        initSettingButton()
    }

    private fun selectStatsItem() {
        binder.navigation.menu.getItem(0).isChecked = true
    }

    private fun selectHomeFragment(){
        binder.navigation.menu.getItem(1).isChecked = true
    }


    private fun selectHabitsItem() {
//        binder.navigation.menu.getItem(1).isChecked = true
    }

    private fun selectTasksItem() {
//        binder.navigation.menu.getItem(2).isChecked = true
    }

    override fun onClickMark() {
        setMarkFragment()
    }

    override fun onClickAddTask() {
        setAddGoalFragment()
    }

    override fun onTaskIsAdded() {
        setTasksFragment()
    }

    override fun onClickAddHabit() {
        setAddHabitFragment()
    }

    override fun onHabitAdded() {
        setHabitsFragment()
    }

    private fun setStatsFragment() {
        val fragment = StatsMenuFragment.newInstance()
        fragment.onClickShowStatsListener = object : StatsMenuFragment.OnClickShowStatsListener {

            override fun onClickShowActivityStats() {
                showActivityStatsFragment()
            }

            override fun onClickShowUsefulnessStats() {
                showUsefulnessStatsFragment()
            }

        }
        setFragment(R.id.container, fragment, true)
        binder.toolbar!!.title.text = getString(R.string.stats)
    }

    private fun showActivityStatsFragment(){
        setFragment(R.id.container, ActivityStatsFragment.newInstance(), true)
        binder.toolbar!!.title.text = getString(R.string.stats_activity)
    }

    private fun showUsefulnessStatsFragment(){
        setFragment(R.id.container, StatsUsefulnessFragment.newInstance(), true)
        binder.toolbar!!.title.text = getString(R.string.stats_usefulness)
    }

    private fun setHomeFragment() {
        setFragment(R.id.container, HomeFragment.newInstance(), true)
        binder.toolbar!!.title.text = presenter.getTextDate()
    }

    private fun setTasksFragment() {
        val fragment = TasksFragment.newInstance()
        fragment.onClickAddTaskListener = this
        setFragment(R.id.container, fragment, true)
        binder.toolbar!!.title.text = getString(R.string.Tasks)
    }

    private fun setAddGoalFragment() {
        setFragment(R.id.container, AddTaskFragment.newInstance(this), true)
        binder.toolbar!!.title.text = getString(R.string.task_adding)
    }

    private fun setHabitsFragment() {
        setFragment(R.id.container, HabitsFragment.newInstance(this), true)
        binder.toolbar!!.title.text = getString(R.string.habits)
    }

    private fun setAddHabitFragment() {
        setFragment(R.id.container, AddHabitFragment.newInstance(this), true)
        binder.toolbar!!.title.text = getString(R.string.addind_habit)
    }

    private fun setMarkFragment() {
        setFragment(R.id.container, MarkFragment.newInstance(), true)
        binder.toolbar!!.title.text = presenter.getTextDate()
    }

    private fun setSettingFragment() {
        setFragment(R.id.container, SettingFragment.newInstance(), true)
        binder.toolbar!!.title.text = getString(R.string.settings)
    }

    override fun showUploadingActionsActivity() {
        startActivity(Intent(this, UploadingActionsActivity::class.java))
    }

    private fun setCurrentBottomItemSelection() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        when (fragment) {
            is StatsUsefulnessFragment -> selectStatsItem()
            is HabitsFragment -> selectHabitsItem()
            is TasksFragment -> selectTasksItem()
            else -> selectHomeFragment()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setCurrentBottomItemSelection()
    }
}
