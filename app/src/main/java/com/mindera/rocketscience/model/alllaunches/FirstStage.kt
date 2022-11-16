package com.mindera.rocketscience.model.alllaunches

import com.google.gson.annotations.SerializedName

data class FirstStage(
    @SerializedName("cores") var cores: ArrayList<Cores> = arrayListOf()
)