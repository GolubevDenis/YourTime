package com.your.time.app.services.action_icon_resources

import com.your.time.app.R
import com.your.time.app.domain.services.ActionIconResourcesService

class ActionIconResourcesServiceImpl : ActionIconResourcesService {

    companion object {
        const val ACTION1_ID = 1
        const val ACTION2_ID = 2
        const val ACTION3_ID = 3
        const val ACTION4_ID = 4
        const val ACTION5_ID = 5
        const val ACTION_FACEBOOK = 6
    }

    private val map = mapOf(
            ACTION1_ID to R.drawable.add_black,
            ACTION2_ID to R.drawable.notifications_black_24dp,
            ACTION3_ID to R.drawable.error_white,
            ACTION4_ID to R.drawable.home_black,
            ACTION5_ID to R.drawable.abc_ic_star_half_black_48dp,
            ACTION_FACEBOOK to R.drawable.ic_facebook
    )

    override fun getResourceIdByActionIconId(actionId: Int?): Int? {
            if(actionId == null) return null

        return map[actionId]
    }

    override fun getActionIconIdByResourceId(applicationIconId: Int?): Int? {
        if(applicationIconId == null) return null

        return map.entries
                .find { it.value == applicationIconId }?.key
    }
}