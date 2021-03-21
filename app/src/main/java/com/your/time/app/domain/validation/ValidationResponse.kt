package com.your.time.app.domain.validation

interface ValidationResponse {

    fun isValidData(): Boolean
    fun getExpansion(): String
}