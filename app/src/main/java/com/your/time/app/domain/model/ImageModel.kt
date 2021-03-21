package com.your.time.app.domain.model

data class ImageModel(
        val imageName: String,
        var isSelected: Boolean = false,
        val tags: List<String> = arrayListOf()
)