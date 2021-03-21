package com.your.time.app.domain.interactors.data.action.uploading

import io.reactivex.Observable

interface UploadingFirstActionsInteractor {

    fun uploadFirstActions(): Observable<Int>
}