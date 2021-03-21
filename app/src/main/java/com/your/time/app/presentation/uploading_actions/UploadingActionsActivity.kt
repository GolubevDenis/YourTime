package com.your.time.app.presentation.uploading_actions

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.your.time.app.MyApplication
import com.your.time.app.R
import com.your.time.app.databinding.ActivityUploadingActionsBinding
import com.your.time.app.di.presentation.uploading_actions.DaggerUploadingActionsComponent
import com.your.time.app.di.presentation.uploading_actions.UploadingActionsComponent
import com.your.time.app.presentation.uploading_actions.mvp.UploadingActionsPresenter
import com.your.time.app.presentation.uploading_actions.mvp.UploadingActionsView
import com.your.time.app.presentation.explansions.showSimpleMessage
import com.your.time.app.presentation.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity

class UploadingActionsActivity : MvpActivity<UploadingActionsView, UploadingActionsPresenter>(), UploadingActionsView {

    override fun showProgressUploading(progress: Int) {
        binder.progress.progress = progress.toFloat()
    }

    override fun showErrorMessage(message: String) {
        showSimpleMessage(message)
    }

    override fun showMainView() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private lateinit var binder: ActivityUploadingActionsBinding

    private companion object {
        private const val LAYOUT = R.layout.activity_uploading_actions
    }

    override fun createPresenter(): UploadingActionsPresenter
            = component.getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(LAYOUT)

        binder = DataBindingUtil.setContentView(this, LAYOUT)
    }

    private val component: UploadingActionsComponent by lazy {
        DaggerUploadingActionsComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
    }

    override fun showInfo(info: String) {
        runOnUiThread {
            binder.loadingText.text = info
        }
    }
}
