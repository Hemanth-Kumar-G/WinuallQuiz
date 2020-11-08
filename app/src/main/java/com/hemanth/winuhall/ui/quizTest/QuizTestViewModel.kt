package com.hemanth.winuhall.ui.quizTest

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hemanth.winuhall.common.Constants
import com.hemanth.winuhall.common.NoConnectivityException
import com.hemanth.winuhall.data.model.QuizQuestionResponse
import com.hemanth.winuhall.data.model.ResultResponse
import com.hemanth.winuhall.data.repository.HomeRepository
import com.hemanth.winuhall.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class QuizTestViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    lateinit var resultResponse: ResultResponse
    val index = ObservableInt(1)
    val loading = ObservableBoolean(true)
    val internetAvailable = ObservableBoolean(true)
    lateinit var quizList: ArrayList<QuizQuestionResponse.Data>

    private val _eventQuizBatch = MutableLiveData<Event<Pair<String, *>>>()
    val eventQuizBatch: LiveData<Event<Pair<String, *>>> = _eventQuizBatch

    private val _eventResult = MutableLiveData<Event<ResultResponse>>()
    val eventResult: LiveData<Event<ResultResponse>> = _eventResult

    fun setLoading(load: Boolean) {
        loading.set(load)
    }

    fun getQuizQuestion() {
        setLoading(true)
        val disposableObserver =
            object : DisposableSingleObserver<Response<QuizQuestionResponse>>() {
                override fun onSuccess(value: Response<QuizQuestionResponse>) {
                    when (value.code()) {
                        200 -> {
                            saveResponse(value.body() as QuizQuestionResponse)
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
                    setLoading(false)
                    if (e is NoConnectivityException)
                        _eventQuizBatch.postValue(Event(Pair(Constants.NO_INTERNET, e.message)))
                    else
                        _eventQuizBatch.postValue(Event(Pair(Constants.ERROR, e.message)))
                }
            }

        homeRepository.getQuizQuestion().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)
    }

    private fun saveResponse(quizQuestionResponse: QuizQuestionResponse?) {
        quizQuestionResponse?.let {
            quizList.clear()
            quizList.addAll(it.data)
        }
    }

    fun validateAnswer() {
        val questionAttemptInfoList = mutableListOf<Boolean>()
        var correctScore = 0
        var wrongScore = 0
        var correctAnswerCount = 0
        var wrongAnswerCount = 0
        var attemptedQuestion = 0
        var notAttemptedQuestion = 0

        quizList.forEach { data ->

            var attempted = false
            var isCorrectAnswer = false

            data.options.forEach { option ->
                if (option.isSelected) attempted = true
                if (option.isSelected && option.correct) {
                    correctScore += data.positiveMarks
                    correctAnswerCount++
                    isCorrectAnswer = true
                }
            }
            if (!isCorrectAnswer && attempted) {
                wrongAnswerCount++
                wrongScore += data.negativeMarks
            }
            if (attempted) attemptedQuestion++ else notAttemptedQuestion++
            questionAttemptInfoList.add(attempted)
        }

        resultResponse = ResultResponse(
            correctScore, correctAnswerCount, attemptedQuestion, notAttemptedQuestion,
            quizList.size, correctScore + wrongScore, wrongScore, wrongAnswerCount,
            questionAttemptInfoList
        )
        _eventResult.postValue(Event(resultResponse))
    }

}