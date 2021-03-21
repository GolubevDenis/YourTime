package com.your.time.app.domain.model.mappers

interface MultiMapper<F1, F2, T> {
    fun map(from1: F1, from2: F2) : T
    fun map(from1: List<F1>, from2: List<F2>) : List<T> = List<T>(from1.size, { index ->  map(from1[index], from2[index])})
}