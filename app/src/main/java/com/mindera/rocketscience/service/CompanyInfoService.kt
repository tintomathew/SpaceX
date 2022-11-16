package com.mindera.rocketscience.service

import com.mindera.rocketscience.model.companyinfo.CompanyInfo
import retrofit2.Response
import retrofit2.http.GET

interface CompanyInfoService {
    @GET("info")
    suspend fun getComapnyInfo(): Response<CompanyInfo>
}