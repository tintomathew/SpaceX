package com.mindera.rocketscience.listeners

import com.mindera.rocketscience.model.Year

interface FilterListener {
    fun filterByLaunchSuccess()
    fun filterByAscending()
    fun filterByDescending()
    fun filterByYearSelected(year: Year)
    fun clearFilter()
}