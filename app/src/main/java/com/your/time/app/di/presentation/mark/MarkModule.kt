package com.your.time.app.di.presentation.mark

import android.content.Context
import com.your.time.app.di.presentation.ActivityContext
import com.your.time.app.di.presentation.PresentationScope
import com.your.time.app.domain.interactors.mark.MarkInteractor
import com.your.time.app.domain.model.TimePeriodModel
import com.your.time.app.domain.services.SchedulersService
import com.your.time.app.domain.util.time.TimeUtil
import com.your.time.app.presentation.mark.mvp.MarkPresenter
import com.your.time.app.presentation.mark.mvp.MarkPresenterImpl
import com.your.time.app.presentation.mark.adapter.MarkTimePeriodHolder
import com.your.time.app.presentation.mark.adapter.MarkTimePeriodsAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapter
import com.your.time.app.presentation.view.adapters.list.ListForAdapterBase
import com.your.time.app.presentation.view.time_period.factory.TimePeriodViewFactory
import com.your.time.app.presentation.view.time_period.factory.TimePeriodViewFactoryImpl
import dagger.Module
import dagger.Provides

@Module
class MarkModule {

    @PresentationScope
    @Provides
    fun providePresenter(
            timeInteractor: MarkInteractor,
            schedulersService: SchedulersService,
            timeUtil: TimeUtil
    ): MarkPresenter
            = MarkPresenterImpl(timeInteractor, schedulersService, timeUtil)

    @PresentationScope
    @Provides
    fun provideRecyclerViewAdapter(
            listItems: ListForAdapter<TimePeriodModel, MarkTimePeriodHolder>,
            factory: TimePeriodViewFactory
    ): MarkTimePeriodsAdapter
            = MarkTimePeriodsAdapter(listItems, factory)

    @PresentationScope
    @Provides
    fun provideListForAdapter(): ListForAdapter<TimePeriodModel, MarkTimePeriodHolder>
            = ListForAdapterBase<TimePeriodModel, MarkTimePeriodHolder>()

    @PresentationScope
    @Provides
    fun provideTimePeriodViewFactory(
            @ActivityContext context: Context,
            timeUtil: TimeUtil
    ): TimePeriodViewFactory
            = TimePeriodViewFactoryImpl(context, timeUtil)
}