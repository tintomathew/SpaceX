package com.mindera.rocketscience.repository

import com.mindera.rocketscience.model.alllaunches.Launch
import com.mindera.rocketscience.service.AllLaunchesService
import com.mindera.rocketscience.util.Resource
import retrofit2.Response
import javax.inject.Inject

class AllLaunchesRepository @Inject constructor(private val allLaunchesService: AllLaunchesService) {
    suspend fun getAllLaunches(
    ): Resource<List<Launch>> {
        return responseToResource(allLaunchesService.getAllLaunches())
    }

    private fun responseToResource(dataModel: Response<List<Launch>>): Resource<List<Launch>> {
        if (dataModel.isSuccessful) {
            dataModel.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(dataModel.message())
    }
}