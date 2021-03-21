package com.your.time.app.domain.services

interface ResourcesService {

    fun getString(id: Int, vararg args: Any): String
    fun getStringMinutes(minutes: Int): String
    fun getStringHours(hours: Int): String
    fun getStringDays(days: Int): String
    fun getMinutesReduction(): String
    fun getHoursReduction(): String
    fun getTitleMarkingNotification(): String
    fun getTextMarkingNotification(unmarkedTime: String): String
    fun getIconMarkingNotification(): Int
    fun getTitleUnmarkedAction(): String
    fun getColorUnmarkedAction(): Int

    fun getTitleClearAction(): String
    fun getColorClearAction(): Int

    /* validation */
    fun getTooShortEmail(length: Int, minLength: Int): String
    fun getCorrectEmail(): String
    fun getIncorrectEmail(): String
    fun getTooShortPassword(length: Int, minLength: Int): String
    fun getCorrectPassword(): String
    fun getWeakPassword(): String
    fun getNormalPassword(): String
    fun getStrongPassword(): String
    fun getVeryStrongPassword(): String
    /*            */

    fun getTimeInfoArray(): Array<out String>?
}