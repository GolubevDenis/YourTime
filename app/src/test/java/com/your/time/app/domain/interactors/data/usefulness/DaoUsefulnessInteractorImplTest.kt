package com.your.time.app.domain.interactors.data.usefulness

import com.your.time.app.*
import com.your.time.app.domain.interactors.data.time_period.DaoTimePeriodInteractor
import com.your.time.app.domain.model.TimePeriodModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class DaoUsefulnessInteractorImplTest {

    private lateinit var daoTimePeriodInteractor: DaoTimePeriodInteractor
    private lateinit var usefulnessInteractor: UsefulnessInteractor

    @Before
    fun init() {
        daoTimePeriodInteractor = Mockito.mock(DaoTimePeriodInteractor::class.java)
        usefulnessInteractor = UsefulnessInteractorImpl(daoTimePeriodInteractor)
    }

    @Test
    fun getUsefulnessOfCurrentDay() {

        val list1 = listOf(
                TimePeriodModel(veryUsefullyAction, 0, 1),
                TimePeriodModel(veryHarmfullyAction, 2, 3),
                TimePeriodModel(neutrallyAction, 4, 5)
        )
        Mockito.`when`(daoTimePeriodInteractor.getTimePeriodsByTodayWithUnmarkedPeriods()).thenReturn(Single.just(list1))

        val result1 = usefulnessInteractor.getUsefulnessOfCurrentDay().test().values()[0]
        assert(result1 == 50f)


        val list2 = listOf(
                TimePeriodModel(veryUsefullyAction, 0, 1),
                TimePeriodModel(neutrallyAction, 2, 3)
        )
        Mockito.`when`(daoTimePeriodInteractor.getTimePeriodsByTodayWithUnmarkedPeriods()).thenReturn(Single.just(list2))

        val result2 = usefulnessInteractor.getUsefulnessOfCurrentDay().test().values()[0]
        assert(result2 == 100f)


        val list3 = listOf(
                TimePeriodModel(veryUsefullyAction, 1, 8),
                TimePeriodModel(veryHarmfullyAction, 9, 10)
        )
        Mockito.`when`(daoTimePeriodInteractor.getTimePeriodsByTodayWithUnmarkedPeriods()).thenReturn(Single.just(list3))

        val result3 = usefulnessInteractor.getUsefulnessOfCurrentDay().test().values()[0]
        assert(result3 == 80f)



        val list4 = listOf(
                TimePeriodModel(veryUsefullyAction, 1, 2),
                TimePeriodModel(usefullyAction, 3, 5),
                TimePeriodModel(neutrallyAction, 6, 7),
                TimePeriodModel(harmfullyAction, 8, 9),
                TimePeriodModel(veryHarmfullyAction, 10, 11)
        )
        Mockito.`when`(daoTimePeriodInteractor.getTimePeriodsByTodayWithUnmarkedPeriods()).thenReturn(Single.just(list4))

        val result4 = usefulnessInteractor.getUsefulnessOfCurrentDay().test().values()[0]
        assert(result4 == 50f)
    }
}