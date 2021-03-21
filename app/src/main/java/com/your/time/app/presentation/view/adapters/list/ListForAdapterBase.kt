package com.your.time.app.presentation.view.adapters.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.your.time.app.presentation.view.adapters.diff.DiffCallbackFactory

open class ListForAdapterBase<M, VH : RecyclerView.ViewHolder> : ListForAdapter<M, VH> {

    protected val items = arrayListOf<M>()
    protected var adapter: RecyclerView.Adapter<VH>? = null
    protected var diff: DiffCallbackFactory<M>? = null

    override fun setupAdapter(adapter: RecyclerView.Adapter<VH>) {
        this.adapter = adapter
    }

    override fun setupDiffCallbackFactory(diff: DiffCallbackFactory<M>) {
        this.diff = diff
    }

    override fun getItems(): List<M> = items

    override fun clearItems(){
        items.clear()
        adapter?.notifyDataSetChanged()
    }

    override fun deleteItems(deletedItems: List<M>){

        if(diff != null){

            val newItems = ArrayList(items)
            newItems.removeAll(deletedItems)

            val diffUtilCallback = diff!!.build(this.items, newItems)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

            this.items.clear()
            this.items.addAll(newItems)

            diffResult.dispatchUpdatesTo(adapter)
        }else{
            items.removeAll(deletedItems)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun setItems(items: List<M>){

        if(diff != null){
            val diffUtilCallback = diff!!.build(this.items, items)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

            this.items.clear()
            this.items.addAll(items)

            diffResult.dispatchUpdatesTo(adapter)
        }else{
            this.items.clear()
            this.items.addAll(items)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun addItems(newItems: List<M>){
        val size = items.size
        items.addAll(newItems)
        adapter?.notifyItemRangeInserted(size, newItems.size)
    }

    override fun move(from: Int, to: Int) {
        val item = getItems()[from]
        items.removeAt(from)
        items.add(to, item)
        adapter?.notifyItemMoved(from, to)
    }

    override fun deleteItem(deletedItem: M) {
        val position = items.indexOf(deletedItem)
        items.removeAt(position)
        adapter?.notifyItemRemoved(position)
    }

    override fun addItem(newItem: M) {
        items.add(newItem)
        adapter?.notifyItemInserted(items.size - 1)
    }
}