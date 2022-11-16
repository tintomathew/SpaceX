package com.mindera.rocketscience.model.companyinfo

import com.google.gson.annotations.SerializedName

data class Links (
    @SerializedName("website"      ) var website     : String? = null,
    @SerializedName("flickr"       ) var flickr      : String? = null,
    @SerializedName("twitter"      ) var twitter     : String? = null,
    @SerializedName("elon_twitter" ) var elonTwitter : String? = null
)