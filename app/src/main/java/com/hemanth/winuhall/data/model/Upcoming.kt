package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class Upcoming(
    @SerializedName("deletedAt")
    var deletedAt: Any,
    @SerializedName("quizDetails")
    var quizDetails: List<QuizDetailX>,
    @SerializedName("scheduleEnd")
    var scheduleEnd: String,
    @SerializedName("scheduleStart")
    var scheduleStart: String
)