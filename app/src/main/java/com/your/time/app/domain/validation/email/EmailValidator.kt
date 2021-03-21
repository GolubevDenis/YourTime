package com.your.time.app.domain.validation.email

import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.validation.DataValidator
import com.your.time.app.domain.validation.SimpleValidationResponse
import com.your.time.app.domain.validation.ValidationResponse

class EmailValidator(
        private val resourcesService: ResourcesService
) : DataValidator {

    private companion object {
        const val MIN_LENGTH = 8
    }

    fun getMinLength() = MIN_LENGTH

    override fun validate(data: String): ValidationResponse {
        var isValid = true
        var explanation = resourcesService.getCorrectEmail()

        if(data.length < getMinLength()){
            isValid = false
            explanation = resourcesService.getTooShortEmail(data.length, getMinLength())
            return SimpleValidationResponse(isValid, explanation)
        }


        val ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = java.util.regex.Pattern.compile(ePattern)
        val m = p.matcher(data)
        m.matches()
        if(!m.matches()){
            isValid = false
            explanation = resourcesService.getIncorrectEmail()
            return SimpleValidationResponse(isValid, explanation)
        }

        return SimpleValidationResponse(isValid, explanation)
    }
}