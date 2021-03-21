package com.your.time.app.presentation.mvp.mark

import android.graphics.Color
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.presentation.mark.mvp.MarkPresenterImpl
import com.your.time.app.presentation.mark.mvp.MarkView
import io.reactivex.Completable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MarkPresenterImplTest : Assert(){

    private lateinit var interactor: MarkInteractor
    private lateinit var presenter: MarkPresenterImpl
    private lateinit var view: MarkView

    val action = ActionModel("any action", color = Color.BLUE)
    private val timePeriod = TimePeriodModel(action, 1 ,10)

    @Before
    fun init(){
        interactor = Mockito.mock(MarkInteractor::class.java)
        view = Mockito.mock(MarkView::class.java)
        presenter = MarkPresenterImpl(interactor)
    }

    @Test
    fun testOnActionsSelected_ShowErrorMessageIfError(){
        Mockito.`when`(interactor.newTimePeriodsByActions(Mockito.anyList())).thenReturn(Completable.error(Exception("error")))
        presenter.attachView(view)

        presenter.onActionsSelected(listOf())
        Mockito.verify(view).showErrorMessage(Mockito.anyString())
    }

    @Test
    fun testOnActionsSelected_CallsInteractorMethod(){
        Mockito.`when`(interactor.newTimePeriodsByActions(Mockito.anyList())).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.onActionsSelected(listOf())
        Mockito.verify(interactor).newTimePeriodsByActions(listOf())
    }

    @Test
    fun testSubscribeOnInit(){
        val presenter = MarkPresenterImpl(interactor)
        Mockito.verify(interactor).subscribeOnTimePeriodsChanging(presenter)
    }

    @Test
    fun testOnClickTimePeriodClose_InvokeInteractorDeleteMethod(){
        presenter.onClickTimePeriodClose(timePeriod)
        Mockito.verify(interactor).deleteTimePeriod(timePeriod)
    }

    @Test
    fun testOnTimePeriodRemoved_InvokeViewDeleteMethod(){
        presenter.attachView(view)

        presenter.onTimePeriodRemoved(timePeriod)
        Mockito.verify(view).showRemovingTimePeriod(timePeriod)
    }

    @Test
    fun testOnTimePeriodAdded_InvokeViewAddingMethod(){
        presenter.attachView(view)

        presenter.onTimePeriodAdded(timePeriod)
        Mockito.verify(view).showAddingTimePeriod(timePeriod)
    }

    @Test
    fun testOnTimePeriodUpdated_InvokeViewUpdationgMethod(){
        presenter.attachView(view)

        presenter.onTimePeriodUpdated(timePeriod, 1)
        Mockito.verify(view).showUpdatingTimePeriod(1)
    }

    @Test
    fun testOnClickOk_CallsInteractorCommitMethod(){
        presenter.onClickOk()
        Mockito.verify(interactor).commitTimePeriods()
    }

    @Test
    fun testOnClickOk_CallsViewShowSuccessCommit(){
        presenter.attachView(view)

        presenter.onClickOk()
        Mockito.verify(view).showCommitSuccess()
    }
}