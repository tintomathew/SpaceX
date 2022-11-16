package com.mindera.rocketscience.model.companyinfo

import com.google.gson.annotations.SerializedName

data class CompanyInfo(
    @SerializedName("name") var name: String? = null,
    @SerializedName("founder") var founder: String? = null,
    @SerializedName("founded") var founded: Int? = null,
    @SerializedName("employees") var employees: Int? = null,
    @SerializedName("vehicles") var vehicles: Int? = null,
    @SerializedName("launch_sites") var launchSites: Int? = null,
    @SerializedName("test_sites") var testSites: Int? = null,
    @SerializedName("ceo") var ceo: String? = null,
    @SerializedName("cto") var cto: String? = null,
    @SerializedName("coo") var coo: String? = null,
    @SerializedName("cto_propulsion") var ctoPropulsion: String? = null,
    @SerializedName("valuation") var valuation: Double? = null,
    @SerializedName("headquarters") var headquarters: Headquarters? = Headquarters(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("summary") var summary: String? = null
)