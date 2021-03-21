package com.your.time.app.domain.model.mappers

interface Mapper<F, T> {
    fun map(from: F) : T
    fun map(from: List<F>) : List<T> = List<T>(from.size, { index ->  map(from[index])})
}