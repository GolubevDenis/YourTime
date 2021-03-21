package com.your.time.app.domain.validation.password

import com.your.time.app.domain.services.ResourcesService

class PasswordValidatorImpl(
        private val resourcesService: ResourcesService
) : PasswordValidator {

    private companion object {
        const val MIN_LENGTH = 6
        const val NORMAL = 8
        const val STRONG = 10
        const val VERY_STRONG = 12
    }

    fun getMinLength() = MIN_LENGTH

    override fun validate(data: String): PasswordValidationResponse {

        if(data.length < getMinLength()){
            return PasswordValidationResponse(
                    false,
                    resourcesService.getTooShortPassword(data.length, getMinLength()),
                    PasswordComplexity.TOO_WEAK
            )
        }else if(data.length == MIN_LENGTH){
            return PasswordValidationResponse(
                    true,
                    resourcesService.getWeakPassword(),
                    PasswordComplexity.WEAK)
        }else if(data.length <= NORMAL){
            return PasswordValidationResponse(
                    true,
                    resourcesService.getNormalPassword(),
                    PasswordComplexity.NORMAL)
        }else if(data.length <= STRONG){
            return PasswordValidationResponse(
                    true,
                    resourcesService.getStrongPassword(),
                    PasswordComplexity.STRONG)
        }else {
            return PasswordValidationResponse(
                    true,
                    resourcesService.getVeryStrongPassword(),
                    PasswordComplexity.VERY_STRONG)
        }
    }
}