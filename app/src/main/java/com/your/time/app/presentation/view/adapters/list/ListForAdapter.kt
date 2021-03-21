package com.your.time.app.presentation.view.adapters.list

import android.support.v7.widget.RecyclerView
import com.your.time.app.presentation.view.adapters.diff.DiffCallbackFactory

interface ListForAdapter<M, VH : RecyclerView.ViewHolder>{

    fun setupAdapter(adapter: RecyclerView.Adapter<VH>)
    fun clearItems()
    fun deleteItems(deletedItems: List<M>)
    fun deleteItem(deletedItem: M)
    fun setItems(items:List<M>)
    fun addItems(newItems: List<M>)
    fun addItem(newItem: M)
    fun getItems(): List<M>
    fun move(from: Int, to: Int)
    fun setupDiffCallbackFactory(diff: DiffCallbackFactory<M>)
}