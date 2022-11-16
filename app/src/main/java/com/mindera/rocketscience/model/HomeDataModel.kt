package com.mindera.rocketscience.model

import com.mindera.rocketscience.model.alllaunches.Launch
import com.mindera.rocketscience.model.companyinfo.CompanyInfo


data class HomeDataModel(var companyInfo: CompanyInfo? = null, var launch: List<Launch>? = null)