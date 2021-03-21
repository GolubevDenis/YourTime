package com.your.time.app.presentation.view.decorators

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

class ListMarginDecoration(
        context: Context,
        margin: Int
) : RecyclerView.ItemDecoration() {

    private val mMargin: Int

    init {
        val metrics = context.resources.displayMetrics
        mMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin.toFloat(), metrics).toInt()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        outRect.top = mMargin
        outRect.bottom = mMargin
        outRect.right = mMargin
        outRect.left = mMargin
    }
}