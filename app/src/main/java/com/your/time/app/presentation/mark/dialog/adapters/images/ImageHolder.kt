package com.your.time.app.presentation.mark.dialog.adapters.images

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.your.time.app.domain.model.ImageModel
import com.your.time.app.expansions.getDrawableIdByName

class ImageHolder(
        val image: ImageView,
        private val selectColor: Int,
        private val notSelectColor: Int
) : RecyclerView.ViewHolder(image){

    fun bind(imageModel: ImageModel){
        val drawableId = image.context.getDrawableIdByName(imageModel.imageName)
        image.setImageResource(drawableId)

        if(imageModel.isSelected) image.setBackgroundResource(selectColor)
        else image.setBackgroundResource(notSelectColor)
    }
}