package com.your.time.app.presentation.uploading_actions.mvp

import com.hannesdorfmann.mosby3.mvp.MvpView

interface UploadingActionsView: MvpView {

    fun showProgressUploading(progress: Int)
    fun showErrorMessage(message: String)
    fun showMainView()
    fun showInfo(info: String)
}