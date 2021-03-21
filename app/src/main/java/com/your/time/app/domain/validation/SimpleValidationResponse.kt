package com.your.time.app.domain.validation

data class SimpleValidationResponse(
    private val isValid: Boolean = true,
    private val expansion: String = ""
) : ValidationResponse {

    override fun isValidData() = isValid
    override fun getExpansion() = expansion
}