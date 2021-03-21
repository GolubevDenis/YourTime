package com.your.time.app.presentation.mark.dialog.adapters.images

import android.support.v7.widget.RecyclerView
import com.your.time.app.domain.model.ImageModel
import com.your.time.app.presentation.view.adapters.list.ListForAdapter

abstract class ImagesRecyclerAdapter : RecyclerView.Adapter<ImageHolder>(){

    abstract fun getSelectedImageName(): String?
    abstract fun getListImages(): ListForAdapter<ImageModel, ImageHolder>
    abstract fun setOnSelectedImageChanged(listener: (String?) -> Unit)
    abstract fun selectImage(iconName: String)
    abstract fun unselectImages()
}