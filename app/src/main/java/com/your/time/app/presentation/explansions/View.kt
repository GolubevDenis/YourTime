package com.your.time.app.presentation.explansions

import android.animation.ObjectAnimator
import android.view.View

fun View.startMoveAnimationTo(xFrom: Float, yFrom: Float, xTarget: Float, yTarget: Float, duration: Long){
    val animX = ObjectAnimator.ofFloat(this, "x", xFrom, xTarget)
    animX.duration = duration

    val animY = ObjectAnimator.ofFloat(this, "y", yFrom, yTarget)
    animY.duration = duration

    animX.start()
    animY.start()
}

fun View.startMoveAnimationTo(toView: View, duration: Long){
    this.startMoveAnimationTo(this.x, this.y, toView.x, toView.y, duration)
}

fun View.startMoveAnimationTo(xTarget: Float, yTarget: Float, duration: Long){
    this.startMoveAnimationTo(this.x, this.y, xTarget, yTarget, duration)
}

fun View.startMoveAnimationToY(yTarget: Float, duration: Long){
    this.startMoveAnimationTo(this.x, this.y, this.x, yTarget, duration)
}

fun View.startMoveAnimationToX(xTarget: Float, duration: Long){
    this.startMoveAnimationTo(this.x, this.y, xTarget, this.y, duration)
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.switchVisibilityGone() {
    if(isVisible()) gone()
    else visible()
}

fun View.switchVisibilityInvisible() {
    if(isVisible()) invisible()
    else visible()
}