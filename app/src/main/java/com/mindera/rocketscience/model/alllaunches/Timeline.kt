package com.mindera.rocketscience.model.alllaunches

import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("webcast_liftoff") var webcastLiftoff: Int? = null
)