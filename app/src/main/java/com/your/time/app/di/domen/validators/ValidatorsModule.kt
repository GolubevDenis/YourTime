package com.your.time.app.di.domen.validators

import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.validation.DataValidator
import com.your.time.app.domain.validation.email.EmailValidator
import com.your.time.app.domain.validation.password.PasswordValidator
import com.your.time.app.domain.validation.password.PasswordValidatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ValidatorsModule {

    @EmailQualifier
    @Singleton
    @Provides
    fun provideEmailValidator(resourcesService: ResourcesService): DataValidator
            = EmailValidator(resourcesService)

    @PasswordQualifier
    @Singleton
    @Provides
    fun providePasswordValidator(resourcesService: ResourcesService): PasswordValidator
            = PasswordValidatorImpl(resourcesService)
}