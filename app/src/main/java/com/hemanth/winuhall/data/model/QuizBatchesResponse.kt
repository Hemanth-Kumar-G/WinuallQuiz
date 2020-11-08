package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class QuizBatchesResponse(
    @SerializedName("batch")
    var batch: Batch
)