package com.your.time.app.expansions

import android.animation.ObjectAnimator
import android.view.View

fun View.startAlphaAnimation(fromAlpha: Float, targetAlpha: Float, duration: Long){
    val anim = ObjectAnimator.ofFloat(this, "alpha", fromAlpha, targetAlpha)
    anim.duration = duration
    anim.start()
}

fun View.startMoveAnimation(xFrom: Float, yFrom: Float, xTarget: Float, yTarget: Float, duration: Long){
    val animX = ObjectAnimator.ofFloat(this, "x", xFrom, xTarget)
    animX.duration = duration

    val animY = ObjectAnimator.ofFloat(this, "y", yFrom, yTarget)
    animY.duration = duration

    animX.start()
    animY.start()
}