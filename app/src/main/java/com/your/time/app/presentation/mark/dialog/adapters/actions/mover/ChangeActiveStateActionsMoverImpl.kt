package com.your.time.app.presentation.mark.dialog.adapters.mover

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.view.action.ActionView
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

class ChangeActiveStateActionsMoverImpl<T: RecyclerView.ViewHolder>(
        private val list: ListForAdapter<ActionModel, T>
) : ChangeActiveStateActionsMover {

    private var countActiveActions = 0

    override fun getCountActiveActions(): Int = countActiveActions
    override fun incrementCountActiveActions(){
        countActiveActions++
    }

    override fun resetCountActiveActions(){
        countActiveActions = 0
    }

    override fun changeActiveState(view: ActionView, position: Int){

        fun setNoActiveActionState(view: ActionView, position: Int){
            view.isActiveState = false
            val from = position
            val to = countActiveActions--
            list.move(from, to - 1)
        }

        fun setActiveActionState(view: ActionView, position: Int){
            view.isActiveState = true
            val from = position
            val to = countActiveActions++
            list.move(from, to)
        }

        if(view.isActiveState){
            setNoActiveActionState(view,position)
        }else{
            setActiveActionState(view, position)
        }
    }
}

