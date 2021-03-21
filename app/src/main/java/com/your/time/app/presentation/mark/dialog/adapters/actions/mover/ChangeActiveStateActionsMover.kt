package com.your.time.app.presentation.mark.dialog.adapters.mover

import com.your.time.app.presentation.view.action.ActionView

interface ChangeActiveStateActionsMover {
    fun changeActiveState(view: ActionView, position: Int)
    fun getCountActiveActions(): Int
    fun incrementCountActiveActions()
    fun resetCountActiveActions()
}

