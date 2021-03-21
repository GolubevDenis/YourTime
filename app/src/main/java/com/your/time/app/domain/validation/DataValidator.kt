package com.your.time.app.domain.validation

interface DataValidator {

    fun validate(data: String): ValidationResponse
}