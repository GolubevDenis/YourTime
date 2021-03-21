package com.your.time.app.domain.interactors.data.action.uploading

import com.your.time.app.domain.data.repository.ActionsRepository
import com.your.time.app.domain.services.GetFirstActionsService
import com.your.time.app.domain.services.SchedulersService
import io.reactivex.Observable

class UploadingFirstActionsInteractorImpl(
        private val getActionsService: GetFirstActionsService,
        private val repository: ActionsRepository,
        private val schedulersService: SchedulersService
) : UploadingFirstActionsInteractor {

    override fun uploadFirstActions(): Observable<Int> {
        return Observable.create { emitter ->
            getActionsService.getFirstActionsList()
                    .subscribe({
                        val size = it.size
                        var completed = 0

                        fun nextProgress() = (++completed / size.toFloat() * 100).toInt()

                        (0 until size)
                                .map { index -> repository.addAction(it[index]) }
                                .forEach {
                                    it
                                            .subscribeOn(schedulersService.io())
                                            .subscribe {
                                                emitter.onNext(nextProgress())
                                                if(completed >= size)
                                                    emitter.onComplete()
                                            }
                                }
                    }, {
                        emitter.onError(it)
                    })
        }
    }
}