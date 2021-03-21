package com.your.time.app.domain.model.mappers

import io.reactivex.Single

interface AsyncMapper<F, T> {
    fun map(from: F) : Single<T>
}