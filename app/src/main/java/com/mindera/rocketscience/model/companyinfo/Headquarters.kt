package com.mindera.rocketscience.model.companyinfo

import com.google.gson.annotations.SerializedName

data class Headquarters(
    @SerializedName("address") var address: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("state") var state: String? = null
)
