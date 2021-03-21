package com.your.time.app.presentation.view.dialogs

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback

abstract class MvpDialogFragment<V : MvpView, P : MvpPresenter<V>>(
        widthProportion: Float,
        heightProportion: Float
) : BaseDialog(widthProportion, heightProportion), MvpDelegateCallback<V, P>, MvpView {

    protected var mMvpDelegate: FragmentMvpDelegate<V, P>? = null

    protected var mPresenter: P? = null

    abstract override fun createPresenter(): P

    protected fun getMvpDelegate(): FragmentMvpDelegate<V, P> {
        if (mMvpDelegate == null) {
            mMvpDelegate = FragmentMvpDelegateImpl<V, P>(this, this, true, true)
        }
        return mMvpDelegate!!
    }

    override fun getPresenter(): P {
        return mPresenter!!
    }

    override fun setPresenter(presenter: P) {
        this.mPresenter = presenter
    }

    override fun getMvpView(): V {
        return this as V
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMvpDelegate().onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        getMvpDelegate().onDestroy()
    }

    override fun onPause() {
        super.onPause()
        getMvpDelegate().onPause()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate().onResume()
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onStart()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onStop()
    }

    override fun onDetach() {
        super.onDetach()
        getMvpDelegate().onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getMvpDelegate().onSaveInstanceState(outState)
    }
}