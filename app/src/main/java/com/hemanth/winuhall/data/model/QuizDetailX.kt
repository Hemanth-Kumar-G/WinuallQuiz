package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class QuizDetailX(
    @SerializedName("courseId")
    var courseId: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("createdBy")
    var createdBy: String,
    @SerializedName("deletedAt")
    var deletedAt: Any,
    @SerializedName("duration")
    var duration: Int,
    @SerializedName("_id")
    var id: String,
    @SerializedName("instructions")
    var instructions: List<String>,
    @SerializedName("name")
    var name: String,
    @SerializedName("orgId")
    var orgId: String,
    @SerializedName("published")
    var published: Boolean,
    @SerializedName("quizMarks")
    var quizMarks: Int,
    @SerializedName("quizType")
    var quizType: Int,
    @SerializedName("settings")
    var settings: SettingsX,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("__v")
    var v: Int
)