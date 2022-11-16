package com.mindera.rocketscience.listeners

import com.mindera.rocketscience.model.alllaunches.Launch

interface HomeItemListener {
    fun onCompanyInfoClick()
    fun launchItemClick(launchDataModel: Launch)
}