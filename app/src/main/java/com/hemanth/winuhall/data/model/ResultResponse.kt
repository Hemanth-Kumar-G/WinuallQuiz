package com.hemanth.winuhall.data.model


import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@IgnoreExtraProperties
data class ResultResponse(
    @SerializedName("correctAnswerScore")
    var correctAnswerScore: Int,
    @SerializedName("correctAnswered")
    var correctAnswered: Int,
    @SerializedName("totalAttempted")
    var totalAttempted: Int,
    @SerializedName("totalNotAttempted")
    var totalNotAttempted: Int,
    @SerializedName("totalQuestion")
    var totalQuestion: Int,
    @SerializedName("totalScore")
    var totalScore: Int,
    @SerializedName("wrongAnswerScore")
    var wrongAnswerScore: Int,
    @SerializedName("wrongAnswered")
    var wrongAnswered: Int,
    var attemptedQuestionList: List<Boolean>
) : Serializable {
    constructor() : this(
        0, 0, 0,
        0, 0, 0, 0,
        0, listOf()
    )
}