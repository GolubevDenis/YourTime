package com.your.time.app.domain.services

interface ActionIconResourcesService {

    fun getResourceIdByActionIconId(actionId: Int?): Int?
    fun getActionIconIdByResourceId(applicationIconId: Int?): Int?
}