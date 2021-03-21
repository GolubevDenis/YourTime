package com.your.time.app.presentation.mvp.mark.dialog

import android.graphics.Color
import com.your.time.app.domain.interactors.createAction.CreateActionInteractor
import com.your.time.app.domain.interactors.data.action.dao.DaoActionsInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsPresenterImpl
import com.your.time.app.presentation.mark.dialog.mvp.ChooseActionsView
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ChooseActionsPresenterImplTest {

    private lateinit var interactor: DaoActionsInteractor
    private lateinit var createActionInteractor: CreateActionInteractor
    private lateinit var presenter: ChooseActionsPresenterImpl
    private lateinit var view: ChooseActionsView
    private lateinit var schedulersService: SchedulersService
    private val action = ActionModel("running", color = Color.BLUE)

    @Before
    fun init(){
        interactor = Mockito.mock(DaoActionsInteractor::class.java)
        schedulersService = Mockito.mock(SchedulersService::class.java)
        Mockito.`when`(schedulersService.io()).thenReturn(Schedulers.trampoline())
        Mockito.`when`(schedulersService.ui()).thenReturn(Schedulers.trampoline())

        createActionInteractor = Mockito.mock(CreateActionInteractor::class.java)

        presenter = ChooseActionsPresenterImpl(interactor, createActionInteractor, schedulersService)
        view = Mockito.mock(ChooseActionsView::class.java)
    }

    @Test
    fun testLoadActionsCall_CallsShowActionsIfSuccess(){
        val actionsList = listOf<ActionModel>()
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(actionsList))

        presenter.attachView(view)
        Mockito.verify(view).showActions(actionsList)
    }

    @Test
    fun testLoadActionsCall_CallsShowErrorIfError(){
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.error(Exception("error message")))

        presenter.attachView(view)
        Mockito.verify(view).showError(Mockito.anyString())
    }

    @Test
    fun testOnAttachView_FirstAttachCallsLoadActions(){
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(listOf()))

        presenter.attachView(view)
        Mockito.verify(view).showActions(Mockito.anyList())
    }

    @Test
    fun testOnClickSearch_ShowActionIfSuccess(){
        val QUERY = "query"
        Mockito.`when`(interactor.queryActiveActionByTitle(QUERY)).thenReturn(Single.just(listOf()))
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(listOf()))

        presenter.attachView(view)
        presenter.onClickSearch(QUERY)
        Mockito.verify(view).showNoActiveActions(Mockito.anyList())
    }

    @Test
    fun testOnClickSearch_ShowErrorIfError(){
        val QUERY = "query"
        val error = Exception("any exception")
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(listOf()))
        Mockito.`when`(interactor.queryActiveActionByTitle(QUERY)).thenReturn(Single.error(error))
        presenter.attachView(view)

        presenter.onClickSearch(QUERY)
        Mockito.verify(view).showError(Mockito.anyString())
    }

    @Test
    fun testClickAddAction_ShowActionAdded(){
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(listOf()))
        Mockito.`when`(interactor.addAction(action)).thenReturn(Completable.complete())
        presenter.attachView(view)

        presenter.onClickAddAction(action)
        Mockito.verify(view).showActionAdded(action)
    }

    @Test
    fun testClickAddAction_ShowErrorAdding(){
        Mockito.`when`(interactor.getAllActions()).thenReturn(Single.just(listOf()))
        Mockito.`when`(interactor.addAction(action)).thenReturn(Completable.error(Exception("")))
        presenter.attachView(view)

        presenter.onClickAddAction(action)
        Mockito.verify(view).showError(Mockito.anyString())
    }
}