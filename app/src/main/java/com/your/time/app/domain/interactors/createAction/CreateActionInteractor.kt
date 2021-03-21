package com.your.time.app.domain.interactors.createAction

import com.your.time.app.domain.model.ImageModel

interface CreateActionInteractor {
    fun getAllImages(): List<ImageModel>
    fun searchImages(query: String): List<ImageModel>
}