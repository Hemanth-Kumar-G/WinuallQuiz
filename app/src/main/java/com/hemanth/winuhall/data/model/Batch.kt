package com.hemanth.winuhall.data.model


import com.google.gson.annotations.SerializedName

data class Batch(
    @SerializedName("completed")
    var completed: List<Completed>,
    @SerializedName("ongoing")
    var ongoing: List<Ongoing>,
    @SerializedName("upcoming")
    var upcoming: List<Upcoming>
)