package com.your.time.app.presentation.mark.dialog.adapters.list

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.presentation.home.adapters.actions.ActionHomeHolder
import com.your.time.app.presentation.mark.dialog.adapters.mover.ChangeActiveStateActionsMover
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import java.lang.IllegalStateException
import kotlin.collections.ArrayList

class ListForActionsAdapterImpl<T: RecyclerView.ViewHolder>
    : ListForAdapterBase<ActionModel, T>(),  ListForActionsAdapter<T> {

    private var mover: ChangeActiveStateActionsMover? = null

    override fun setMover(mover: ChangeActiveStateActionsMover) {
        this.mover = mover
    }

    override fun markAllActionsAsNotActive(){
        mover!!.resetCountActiveActions()
        adapter?.notifyDataSetChanged()
    }

    override fun addActiveItem(action: ActionModel) {
        val indexOfAction = items.indexOf(action)
        if(indexOfAction != -1){
            items[indexOfAction] = action
            if(indexOfAction > mover!!.getCountActiveActions()){
                mover!!.incrementCountActiveActions()
            }
            adapter?.notifyItemChanged(indexOfAction)
        }else {
            items.add(mover!!.getCountActiveActions(), action)
            mover!!.incrementCountActiveActions()
            adapter?.notifyDataSetChanged()
        }
    }

    override fun setNoActiveActions(noActiveActions: List<ActionModel>){
        val oldNoActiveActions = getNoActiveActions()
        items.removeAll(oldNoActiveActions)

        fun addUnique(newItems: List<ActionModel>) {
            newItems.forEach { if(!items.contains(it)) items.add(it) }
        }

        addUnique(noActiveActions)
        adapter?.notifyDataSetChanged()
    }

    override fun addNoActiveAction(noActiveAction: ActionModel){
        if(!items.contains(noActiveAction)) addItem(noActiveAction)
    }

    override fun clearNoActiveActions(){
        val noActiveAction = getNoActiveActions()
        deleteItems(noActiveAction)
    }

    private fun getNoActiveActions(): List<ActionModel>{
        val copyList = ArrayList<ActionModel>(items)
        return copyList.subList(mover!!.getCountActiveActions(), copyList.size)
    }

    override fun getActiveActions(): List<ActionModel> {
        if(mover == null) throw IllegalStateException("Set mover via setMover()")
        val countActiveActions = mover!!.getCountActiveActions()
        if (countActiveActions == 0) return emptyList()
        return getItems().subList(0, countActiveActions)
    }
}