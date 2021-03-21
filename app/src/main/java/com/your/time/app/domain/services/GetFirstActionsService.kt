package com.your.time.app.domain.services

import com.your.time.app.domain.model.actions.ActionModel
import io.reactivex.Single

interface GetFirstActionsService {

    fun getFirstActionsList(): Single<List<ActionModel>>
}