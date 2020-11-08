package com.hemanth.winuhall.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanth.winuhall.common.Constants
import com.hemanth.winuhall.common.NoConnectivityException
import com.hemanth.winuhall.data.model.Completed
import com.hemanth.winuhall.data.model.Ongoing
import com.hemanth.winuhall.data.model.QuizBatchesResponse
import com.hemanth.winuhall.data.model.Upcoming
import com.hemanth.winuhall.data.repository.HomeRepository
import com.hemanth.winuhall.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

/**
 *<h1>HomeViewModel</h1>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _eventQuizBatch = MutableLiveData<Event<Pair<String, *>>>()
    val eventQuizBatch: LiveData<Event<Pair<String, *>>> = _eventQuizBatch

    lateinit var ongoingList: ArrayList<Ongoing>
    lateinit var upcomingList: ArrayList<Upcoming>
    lateinit var completedList: ArrayList<Completed>

    fun getQuizBatchDetails() {
        val disposableObserver =
            object : DisposableSingleObserver<Response<QuizBatchesResponse>>() {
                override fun onSuccess(value: Response<QuizBatchesResponse>) {
                    when (value.code()) {
                        200 -> {
                            saveResponse(value.body() as QuizBatchesResponse)
                            _eventQuizBatch.postValue(
                                Event(Pair(Constants.SUCCESS, value.body()))
                            )
                        }
                        else -> _eventQuizBatch.postValue(
                            Event(Pair(Constants.ERROR, value.code().toString()))
                        )
                    }
                }

                override fun onError(e: Throwable) {
                    if (e is NoConnectivityException)
                        _eventQuizBatch.postValue(Event(Pair(Constants.NO_INTERNET, e.message)))
                    else
                        _eventQuizBatch.postValue(Event(Pair(Constants.ERROR, e.message)))
                }
            }
        homeRepository.getQuizBatchDetails().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)

    }

    private fun saveResponse(quizBatchResponse: QuizBatchesResponse?) {
        quizBatchResponse?.let {
            ongoingList.clear()
            upcomingList.clear()
            completedList.clear()
            ongoingList.addAll(it.batch.ongoing)
            upcomingList.addAll(it.batch.upcoming)
            completedList.addAll(it.batch.completed)
        }
    }


}