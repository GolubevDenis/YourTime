package com.your.time.app.services.resources

import android.content.Context
import com.your.time.app.R
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.presentation.explansions.color

class ResourcesServiceImpl(
        private val context: Context
) : ResourcesService {

    override fun getString(id: Int, vararg args: Any)
            = context.getString(id, args)

    override fun getStringMinutes(minutes: Int)
            = context.resources.getQuantityString(R.plurals.minutes, minutes)

    override fun getStringHours(hours: Int)
            = context.resources.getQuantityString(R.plurals.hours, hours)

    override fun getStringDays(days: Int)
            = context.resources.getQuantityString(R.plurals.days, days)

    override fun getMinutesReduction()
            = context.resources.getString(R.string.minutes_reduction)

    override fun getHoursReduction()
            = context.resources.getString(R.string.hours_reduction)

    override fun getTitleMarkingNotification()
            = context.resources.getString(R.string.title_marking_notification)

    override fun getTextMarkingNotification(unmarkedTime: String)
            = context.resources.getString(R.string.text_marking_notification, unmarkedTime)

    override fun getIconMarkingNotification()
            = R.drawable.notifications_black_24dp

    override fun getTitleUnmarkedAction()
            = context.getString(R.string.unmarked_action)

    override fun getColorUnmarkedAction()
            = context.color(R.color.light_gray)

    override fun getTitleClearAction() = context.getString(R.string.cleared)

    override fun getColorClearAction() = R.color.clear_action

    override fun getTooShortEmail(length: Int, minLength: Int)
            = context.getString(R.string.too_short_e_mail, length, minLength)

    override fun getCorrectEmail()
            = context.getString(R.string.correct_email)


    override fun getIncorrectEmail()
            = context.getString(R.string.incorrect_email)

    override fun getTooShortPassword(length: Int, minLength: Int)
            = context.getString(R.string.too_short_password, length, minLength)

    override fun getCorrectPassword()
            = context.getString(R.string.correct_password)

    override fun getWeakPassword()
            = context.getString(R.string.weak_password)

    override fun getNormalPassword()
            = context.getString(R.string.normal_password)

    override fun getStrongPassword()
            = context.getString(R.string.strong_password)

    override fun getVeryStrongPassword()
            = context.getString(R.string.very_strong_password)

    override fun getTimeInfoArray() = context.resources.getStringArray(R.array.time_info)
}