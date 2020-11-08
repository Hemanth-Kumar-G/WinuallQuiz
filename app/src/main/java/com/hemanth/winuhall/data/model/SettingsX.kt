package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class SettingsX(
    @SerializedName("attempts")
    var attempts: Int,
    @SerializedName("isSuffle")
    var isSuffle: Boolean,
    @SerializedName("public")
    var `public`: Boolean,
    @SerializedName("security")
    var security: Boolean,
    @SerializedName("sendMessageToParents")
    var sendMessageToParents: Boolean,
    @SerializedName("sendMessageToStudent")
    var sendMessageToStudent: Boolean,
    @SerializedName("solutionReleaseAt")
    var solutionReleaseAt: Any,
    @SerializedName("solutionReleaseType")
    var solutionReleaseType: Int
)