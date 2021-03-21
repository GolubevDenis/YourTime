package com.your.time.app.domain.validation.password

import com.your.time.app.domain.services.ResourcesService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class PasswordValidatorTest : Assert() {

    private lateinit var validator: PasswordValidatorImpl
    private lateinit var resourcesService: ResourcesService

    @Before
    fun init() {
        resourcesService = Mockito.mock(ResourcesService::class.java)
        validator = PasswordValidatorImpl(resourcesService)
    }

    @Test
    fun validate_TOO_WEAK() {
        val password = "123"
        val explanation = "слишком короткий пароль"
        Mockito.`when`(resourcesService.getTooShortPassword(password.length, validator.getMinLength()))
                .thenReturn(explanation)
        val expectedValidationResponse = PasswordValidationResponse(false, explanation, PasswordComplexity.TOO_WEAK)
        val validationResponse = validator.validate(password)

        assertEquals(expectedValidationResponse, validationResponse)
    }

    @Test
    fun validate_WEAK() {
        val password = "123456"
        val explanation = "слабый пароль"
        Mockito.`when`(resourcesService.getWeakPassword())
                .thenReturn(explanation)
        val expectedValidationResponse = PasswordValidationResponse(true, explanation, PasswordComplexity.WEAK)
        val validationResponse = validator.validate(password)

        assertEquals(expectedValidationResponse, validationResponse)
    }

    @Test
    fun validate_NORMAL() {
        val password = "12345678"
        val explanation = "нормальный пароль"
        Mockito.`when`(resourcesService.getNormalPassword())
                .thenReturn(explanation)
        val expectedValidationResponse = PasswordValidationResponse(true, explanation, PasswordComplexity.NORMAL)
        val validationResponse = validator.validate(password)

        assertEquals(expectedValidationResponse, validationResponse)
    }

    @Test
    fun validate_STRONG() {
        val password = "1234567890"
        val explanation = "сильный пароль"
        Mockito.`when`(resourcesService.getStrongPassword())
                .thenReturn(explanation)
        val expectedValidationResponse = PasswordValidationResponse(true, explanation, PasswordComplexity.STRONG)
        val validationResponse = validator.validate(password)

        assertEquals(expectedValidationResponse, validationResponse)
    }

    @Test
    fun validate_VERY_STRONG() {
        val password = "123456789012"
        val explanation = "очень сильный пароль"
        Mockito.`when`(resourcesService.getVeryStrongPassword())
                .thenReturn(explanation)
        val expectedValidationResponse = PasswordValidationResponse(true, explanation, PasswordComplexity.VERY_STRONG)
        val validationResponse = validator.validate(password)

        assertEquals(expectedValidationResponse, validationResponse)
    }

}