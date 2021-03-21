package com.your.time.app.presentation.mark.dialog.adapters.images

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.your.time.app.R
import com.your.time.app.domain.model.ImageModel

class ImagesRecyclerAdapterImpl(
        private val context: Context,
        private val listImages: ListForAdapter<ImageModel, ImageHolder>
) : ImagesRecyclerAdapter(){

    init {
        listImages.setupAdapter(this)
    }

    override fun getListImages(): ListForAdapter<ImageModel, ImageHolder> = listImages

    private companion object {
        const val DEFAULT_SELECT_COLOR = R.color.accent_light
        const val DEFAULT_NOT_SELECT_COLOR = android.R.color.white
    }

    override fun getItemCount() = listImages.getItems().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ImageHolder(createImageView(), DEFAULT_SELECT_COLOR, DEFAULT_NOT_SELECT_COLOR)

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val imageModel = listImages.getItems()[position]
        holder.bind(imageModel)

        fun onClick(){
            imageModel.isSelected = !imageModel.isSelected

            if(imageModel.isSelected) {
                listener?.invoke(imageModel.imageName)
                listImages.getItems()
                        .filter { imageModel != it }
                        .forEach { it.isSelected = false }
            }
            else listener?.invoke(null)

            notifyDataSetChanged()
        }

        holder.image.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION){
                onClick()
            }
        }
    }

    private fun createImageView(): ImageView{
        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT)
        imageView.setTag(imageView.id, false)
        return imageView
    }

    override fun getSelectedImageName(): String? {
        return listImages.getItems()
                .find { it.isSelected }?.imageName
    }

    override fun selectImage(iconName: String) {
        listImages.getItems().forEach { it.isSelected = false }
        listImages.getItems().filter { it.imageName == iconName }.firstOrNull()?.isSelected = true
        notifyDataSetChanged()
    }

    override fun unselectImages() {
        listImages.getItems().forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    private var listener: ((String?) -> Unit)? = null
    override fun setOnSelectedImageChanged(listener: (String?) -> Unit) {
        this.listener = listener
    }
}