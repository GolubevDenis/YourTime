package com.your.time.app.domain.interactors.data.time_period

import android.graphics.Color
import com.your.time.app.domain.data.repository.TimePeriodsRepository
import com.your.time.app.domain.interactors.time.TimeInteractor
import com.your.time.app.domain.interactors.time.TimeInteractorImpl
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.model.actions.ActionFactory
import com.your.time.app.domain.model.actions.ActionModel
import com.your.time.app.domain.services.ResourcesService
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.domain.util.time.TimeUtilImpl
import com.your.time.app.domain.util.time.TimeUtilImplTest
import com.your.time.app.services.resources.ResourcesServiceImpl
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class TimeInteractorImplTest : Assert() {

    private lateinit var timeUtil: TimeUtil
    private lateinit var daoInteractor: TimeInteractor

    @Before
    fun init() {
        timeUtil = Mockito.mock(TimeUtil::class.java)

        daoInteractor = TimeInteractorImpl(timeUtil)
    }

    @Test
    fun test_separatePeriodsToDays(){

        Mockito.`when`(timeUtil.minutesToMilliseconds(1)).thenReturn(1L)
        Mockito.`when`(timeUtil.minutesToMilliseconds(2)).thenReturn(2L)
        Mockito.`when`(timeUtil.minutesToMilliseconds(3)).thenReturn(3L)

        Mockito.`when`(timeUtil.getDateText(1L)).thenReturn("1")
        Mockito.`when`(timeUtil.getDateText(2L)).thenReturn("2")
        Mockito.`when`(timeUtil.getDateText(3L)).thenReturn("3")

        val period1 = TimePeriodModel(ActionModel("1", color = Color.BLUE),  1, 10)
        val period2 = TimePeriodModel(ActionModel("2", color = Color.BLUE),  2, 10)
        val period3 = TimePeriodModel(ActionModel("3", color = Color.BLUE),  3, 10)
        val period4 = TimePeriodModel(ActionModel("4", color = Color.BLUE),  2, 10)
        val period5 = TimePeriodModel(ActionModel("5", color = Color.BLUE),  1, 10)

        val single = Single.just(
                listOf(
                        period1, period2, period3, period4, period5
                )
        )

        val result = daoInteractor.separatePeriodsToDays(single).test().values()[0]
        assertEquals(mapOf(
                "1" to listOf(period1, period5),
                "2" to listOf(period2, period4),
                "3" to listOf(period3)
        ), result)
    }

    @Test
    fun test_mergeAdjacentPeriods(){

        val single = Single.just(
                listOf(
                        TimePeriodModel(ActionModel("1", color = Color.BLUE), 1, 2),
                        TimePeriodModel(ActionModel("1", color = Color.BLUE), 3, 4),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 5, 6),
                        TimePeriodModel(ActionModel("3", color = Color.BLUE), 7, 8),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 9, 10),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 11, 14),
                        TimePeriodModel(ActionModel("4", color = Color.BLUE), 15, 18)
                )
        )

        val result = daoInteractor.mergeAdjacentPeriods(single).test().values()[0]
        assertEquals(listOf(
                TimePeriodModel(ActionModel("1", color = Color.BLUE), 1, 4),
                TimePeriodModel(ActionModel("2", color = Color.BLUE), 5, 6),
                TimePeriodModel(ActionModel("3", color = Color.BLUE), 7, 8),
                TimePeriodModel(ActionModel("2", color = Color.BLUE), 9, 14),
                TimePeriodModel(ActionModel("4", color = Color.BLUE), 15, 18)
        ), result)
    }

    @Test
    fun test_mergeAllPeriods(){

        val single = Single.just(
                listOf(
                        TimePeriodModel(ActionModel("1", color = Color.BLUE), 1, 2),
                        TimePeriodModel(ActionModel("1", color = Color.BLUE), 2, 3),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 3, 4),
                        TimePeriodModel(ActionModel("3", color = Color.BLUE), 4, 5),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 5, 6),
                        TimePeriodModel(ActionModel("2", color = Color.BLUE), 6, 10)
                )
        )

        val result = daoInteractor.mergeAllPeriods(single)
                .map {
                    it.map { it.getDurationInMinutes() }
                }.test().values()[0]

        assertEquals(listOf(
                2,
                6,
                1
        ), result)
    }
}