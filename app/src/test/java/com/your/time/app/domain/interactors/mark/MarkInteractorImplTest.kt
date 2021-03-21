package com.your.time.app.domain.interactors.mark

import android.graphics.Color
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.exceptions.YouAreNotAuthorizedException
import com.your.time.app.domain.interactors.setting.SettingInteractor
import com.your.time.app.domain.model.time.TimeModel
import com.your.time.app.domain.model.time.TimeModelImpl
import com.your.time.app.domain.services.NotificatorService
import com.your.time.app.domain.services.PropertiesService
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MarkInteractorImplTest : Assert() {

    private lateinit var daoInteractor: DaoTimePeriodInteractor
    private lateinit var resourcesService: ResourcesService
    private lateinit var schedulersService: SchedulersService
    private lateinit var settingInteractor: SettingInteractor
    private lateinit var propertiesService: PropertiesService
    private lateinit var notificatorService: NotificatorService

    @Before
    fun init() {
        daoInteractor = Mockito.mock(DaoTimePeriodInteractor::class.java)
        Mockito.`when`(daoInteractor.getLastestTimePeriod())
                .thenReturn(Maybe.error(YouAreNotAuthorizedException()))
        resourcesService = Mockito.mock(ResourcesService::class.java)

        schedulersService = Mockito.mock(SchedulersService::class.java)
        Mockito.`when`(schedulersService.io()).thenReturn(Schedulers.io())

        settingInteractor = Mockito.mock(SettingInteractor::class.java)
        propertiesService = Mockito.mock(PropertiesService::class.java)
        notificatorService = Mockito.mock(NotificatorService::class.java)
    }

    @Test
    fun testNewTimePeriodsByActions_AddCorrectTimePeriodsIfAuthorized(){
        val daoInteractor = getDaoInteractorMock(60)
        val timeUtil = getTimeUtilMock(120)
        val timeModel = TimeModelImpl()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val list1 = getListActions(3)
        val timePeriod1 = TimePeriodModel(list1[0], 60, 80)
        val timePeriod2 = TimePeriodModel(list1[1], 80, 100)
        val timePeriod3 = TimePeriodModel(list1[2], 100, 120)

        interactor.newTimePeriodsByActions(list1).doOnComplete {
            interactor.getActiveTimePeriods().test().assertResult(listOf(timePeriod1, timePeriod2, timePeriod3))
        }

        Mockito.`when`(timeUtil.getCurrentTimeInMinutes()).thenReturn(160)

        val list2 = getListActions(2)
        val timePeriod4 = TimePeriodModel(list2[0], 120, 140)
        val timePeriod5 = TimePeriodModel(list2[1], 140, 160)

        interactor.newTimePeriodsByActions(list2).doOnComplete {
            interactor.getActiveTimePeriods().test().assertResult(listOf(timePeriod1, timePeriod2, timePeriod3, timePeriod4, timePeriod5))
        }
    }

    @Test
    fun testNewTimePeriodsByActions_ErrorIfActionsMoreThenTimePeriodInMinutes(){
        val daoInteractor = getDaoInteractorMock(0)
        val timeUtil = getTimeUtilMock(1)
        val timeModel = TimeModelImpl()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val list = getListActions(100)
        interactor.newTimePeriodsByActions(list)
                .test().assertError(Exception::class.java)
    }

    @Test
    fun testSubscribeOnOnTimePeriodsChanging(){
        val daoInteractor = getDaoInteractorMock(0)
        val timeUtil = getTimeUtilMock(0)
        val timeModel = getTimeModelMock()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val listener = Mockito.mock(TimeModelImpl.OnTimePeriodsChangedListener::class.java)
        interactor.subscribeOnTimePeriodsChanging(listener)

        Mockito.verify(timeModel).addOnTimePeriodsChangedListener(listener)
    }

    @Test
    fun testAddTimePeriod(){
        val daoInteractor = getDaoInteractorMock(0)
        val timeUtil = getTimeUtilMock(0)
        val timeModel = TimeModelImpl()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val action = ActionModel("any action", color = Color.BLUE)
        val timePeriod = TimePeriodModel(action, 0, 10)

        interactor.addTimePeriod(timePeriod)
        assertEquals(1, timeModel.getTimePeriods().size)
    }

    @Test
    fun testDeleteTimePeriod(){
        val daoInteractor = getDaoInteractorMock(0)
        val timeUtil = getTimeUtilMock(0)
        val timeModel = TimeModelImpl()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val action = ActionModel("any action", color = Color.BLUE)
        val timePeriod = TimePeriodModel(action, 0, 10)
        interactor.addTimePeriod(timePeriod)

        interactor.deleteTimePeriod(timePeriod)
        assertEquals(0, timeModel.getTimePeriods().size)
    }

    @Test
    fun testDeleteTimePeriod_CallsVacuum(){
        val daoInteractor = getDaoInteractorMock(0)
        val timeUtil = getTimeUtilMock(0)
        val timeModel = getTimeModelMock()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)

        val action = ActionModel("any action", color = Color.BLUE)
        val timePeriod = TimePeriodModel(action, 0, 10)

        interactor.deleteTimePeriod(timePeriod)
        Mockito.verify(timeModel).vacuum()
    }

    @Test
    fun testCommitTimePeriods_CommitsPeriodsIntoDatabase(){
        val daoInteractor = getDaoInteractorMock(0)
        Mockito.`when`(daoInteractor.addTimePeriods(Mockito.anyList())).thenReturn(Completable.complete())
        val timeUtil = getTimeUtilMock(0)
        val timeModel = TimeModelImpl()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)
        interactor.newTimePeriodsByActions(getListActions(10))

        interactor.commitTimePeriods()
        Mockito.verify(daoInteractor).addTimePeriods(timeModel.getTimePeriods())
    }

    @Test
    fun testCommitTimePeriods_ClearTimeModel(){
        val daoInteractor = getDaoInteractorMock(0)
        Mockito.`when`(daoInteractor.addTimePeriods(Mockito.anyList())).thenReturn(Completable.complete())
        val timeUtil = getTimeUtilMock(0)
        val timeModel = getTimeModelMock()

        val interactor = MarkInteractorImpl(timeModel, timeUtil, daoInteractor, schedulersService,
                settingInteractor, propertiesService, notificatorService)
        interactor.newTimePeriodsByActions(getListActions(10))

        interactor.commitTimePeriods()
        Thread.sleep(100)
        Mockito.verify(timeModel).clear()
    }

    private fun getListActions(count: Int)
        = List(count) { i -> ActionModel(i.toString(), color = Color.BLUE) }

    private fun getTimeModelMock()
            = Mockito.mock(TimeModel::class.java)

    private fun getTimeUtilMock(currentTimeInMinutes: Int): TimeUtil{
        val timeUtil = Mockito.mock(TimeUtil::class.java)
        Mockito.`when`(timeUtil.getCurrentTimeInMinutes()).thenReturn(currentTimeInMinutes)
        return timeUtil
    }

    private fun getDaoInteractorMock(lastTimePeriodEndTime: Int): DaoTimePeriodInteractor{
        val daoInteractor = Mockito.mock(DaoTimePeriodInteractor::class.java)
        val timePeriod = TimePeriodModel(ActionModel("", color = Color.BLUE), 0, lastTimePeriodEndTime)

        Mockito.`when`(daoInteractor.getLastestTimePeriod())
                .thenReturn(Maybe.just(timePeriod))

        return daoInteractor
    }
}