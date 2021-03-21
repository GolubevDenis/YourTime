package com.your.time.app.domain.interactors.createAction

import com.your.time.app.domain.model.ImageModel
import com.your.time.app.domain.services.ActionImagesService

class CreateActionInteractorImpl(
        private val imagesForChoosing: ActionImagesService
) : CreateActionInteractor {

    override fun getAllImages() = imagesForChoosing.getImages()

    override fun searchImages(query: String): List<ImageModel> {
        return getAllImages()
                .filter { it.tags.any { it.contains(query, true) } }
    }
}