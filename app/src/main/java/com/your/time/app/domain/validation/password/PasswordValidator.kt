package com.your.time.app.domain.validation.password

import com.your.time.app.domain.validation.DataValidator

interface PasswordValidator : DataValidator {

    override fun validate(data: String): PasswordValidationResponse
}