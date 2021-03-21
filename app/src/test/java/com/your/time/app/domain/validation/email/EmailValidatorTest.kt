package com.your.time.app.domain.validation.email

import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.validation.SimpleValidationResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class EmailValidatorTest : Assert() {

    private lateinit var validator: EmailValidator
    private lateinit var resourcesService: ResourcesService

    @Before
    fun init() {
        resourcesService = Mockito.mock(ResourcesService::class.java)
        validator = EmailValidator(resourcesService)
    }

    @Test
    fun validateTooShortEmail() {
        val email = "1@g.ru"
        val responseExplanation = "слишком короткая почта"
        Mockito.`when`(resourcesService.getTooShortEmail(email.length, validator.getMinLength())).thenReturn(responseExplanation)
        val validationResponse = SimpleValidationResponse(false, responseExplanation)
        val isCorrect1 = validator.validate(email)
        assertEquals(isCorrect1, validationResponse)
    }

    @Test
    fun validateIncorrectEmail() {
        val email = "некорректная почта"
        val responseExplanation = "некоректная почта"
        Mockito.`when`(resourcesService.getIncorrectEmail()).thenReturn(responseExplanation)
        val validationResponse = SimpleValidationResponse(false, responseExplanation)
        val isCorrect1 = validator.validate(email)
        assertEquals(isCorrect1, validationResponse)
    }

    @Test
    fun validateValidEmail() {
        val email = "email@email.com"
        val responseExplanation = "коректная почта"
        Mockito.`when`(resourcesService.getCorrectEmail()).thenReturn(responseExplanation)
        val validationResponse = SimpleValidationResponse(true, responseExplanation)
        val isCorrect1 = validator.validate(email)
        assertEquals(isCorrect1, validationResponse)
    }
}