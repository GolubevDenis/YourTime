package com.your.time.app.domain.validation.password

import com.your.time.app.domain.validation.ValidationResponse

data class PasswordValidationResponse(
    private val isValid: Boolean = true,
    private val expansion: String = "",
    private val complexity: PasswordComplexity
) : ValidationResponse{

    override fun isValidData() = isValid
    override fun getExpansion() = expansion
    fun getComplexity() = complexity
}