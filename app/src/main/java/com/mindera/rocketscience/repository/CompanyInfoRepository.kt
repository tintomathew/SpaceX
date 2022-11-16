package com.mindera.rocketscience.repository

import com.mindera.rocketscience.model.companyinfo.CompanyInfo
import com.mindera.rocketscience.service.CompanyInfoService
import com.mindera.rocketscience.util.Resource
import retrofit2.Response
import javax.inject.Inject

class CompanyInfoRepository @Inject constructor(private val companyInfoService: CompanyInfoService) {
    suspend fun getCompanyInfo(
    ): Resource<CompanyInfo> {
        return responseToResource(companyInfoService.getComapnyInfo())
    }

    private fun responseToResource(dataModel: Response<CompanyInfo>): Resource<CompanyInfo> {
        if (dataModel.isSuccessful) {
            dataModel.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(dataModel.message())
    }
}