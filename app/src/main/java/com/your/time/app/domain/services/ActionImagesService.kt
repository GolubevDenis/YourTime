package com.your.time.app.domain.services

import com.your.time.app.domain.model.ImageModel

interface ActionImagesService {
    fun getImages(): List<ImageModel>
}