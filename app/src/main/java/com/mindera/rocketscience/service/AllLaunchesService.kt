package com.mindera.rocketscience.service

import com.mindera.rocketscience.model.alllaunches.Launch
import retrofit2.Response
import retrofit2.http.GET

interface AllLaunchesService {
    @GET("launches")
    suspend fun getAllLaunches(): Response<List<Launch>>
}