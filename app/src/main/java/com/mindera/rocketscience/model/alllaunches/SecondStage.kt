package com.mindera.rocketscience.model.alllaunches

import com.google.gson.annotations.SerializedName

data class SecondStage(
    @SerializedName("block") var block: Int? = null,
    @SerializedName("payloads") var payloads: ArrayList<Payloads> = arrayListOf()
)