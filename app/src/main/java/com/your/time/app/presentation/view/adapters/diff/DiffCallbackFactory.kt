package com.your.time.app.presentation.view.adapters.diff

import android.support.v7.util.DiffUtil

interface DiffCallbackFactory<M> {

    fun build(oldData: List<M>, newData: List<M>): DiffUtil.Callback
}