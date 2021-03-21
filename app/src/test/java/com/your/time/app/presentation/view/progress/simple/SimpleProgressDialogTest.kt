package com.your.time.app.presentation.view.progress.simple

import android.support.v7.app.AppCompatActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowDialog

@RunWith(RobolectricTestRunner::class)
class SimpleProgressDialogTest {

    private lateinit var activity: AppCompatActivity
    private lateinit var progressDialog: SimpleProgressDialog

    @Before
    fun init(){
        activity = Robolectric.setupActivity(AppCompatActivity::class.java)
        progressDialog = SimpleProgressDialog(activity)
    }

    @Test
    fun show() {
        val dialogNull = ShadowDialog.getShownDialogs()
        Assert.assertEquals(0, dialogNull.size)

        progressDialog.show()

        val dialogNotNull = ShadowDialog.getShownDialogs()
        Assert.assertTrue(dialogNotNull[0].isShowing)
    }

    @Test
    fun hide() {
        progressDialog.show()
        val dialogNotNull = ShadowDialog.getShownDialogs()
        Assert.assertTrue(dialogNotNull[0].isShowing)

        progressDialog.hide()
        val dialogNull = ShadowDialog.getShownDialogs()
        Assert.assertFalse(dialogNull[0].isShowing)
    }
}