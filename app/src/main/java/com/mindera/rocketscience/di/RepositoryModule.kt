package com.mindera.rocketscience.di

import com.mindera.rocketscience.repository.AllLaunchesRepository
import com.mindera.rocketscience.repository.CompanyInfoRepository
import com.mindera.rocketscience.service.AllLaunchesService
import com.mindera.rocketscience.service.CompanyInfoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAllLaunchesRepository(allLaunchesService: AllLaunchesService): AllLaunchesRepository {
        return AllLaunchesRepository(allLaunchesService)
    }

    @Provides
    fun provideCompanyInfoRepository(companyInfoService: CompanyInfoService): CompanyInfoRepository {
        return CompanyInfoRepository(companyInfoService)
    }
}