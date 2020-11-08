package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class Completed(
    @SerializedName("deletedAt")
    var deletedAt: Any,
    @SerializedName("quizDetails")
    var quizDetails: List<QuizDetail>,
    @SerializedName("scheduleEnd")
    var scheduleEnd: String,
    @SerializedName("scheduleStart")
    var scheduleStart: String
)