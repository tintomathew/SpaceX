package com.mindera.rocketscience

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindera.rocketscience.model.alllaunches.*
import com.mindera.rocketscience.repository.AllLaunchesRepository
import com.mindera.rocketscience.service.AllLaunchesService
import com.mindera.rocketscience.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Response


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class AllLaunchesRepositoryTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    var allLaunchesService: AllLaunchesService = mockk()

    // class under test
    lateinit var allLaunchesRepository: AllLaunchesRepository

    @Before
    fun setup() {
        allLaunchesRepository = AllLaunchesRepository(allLaunchesService)
    }

    @Test
    fun allLaunchSuccessTest() {
        val launchModel = Launch(
            2,
            missionName = "",
            missionId = arrayListOf(),
            upcoming = null,
            launchYear = null,
            launchDateUnix = null,
            launchDateUtc = null,
            launchDateLocal = null,
            isTentative = null,
            tentativeMaxPrecision = null,
            tbd = null,
            launchWindow = null,
            rocket = Rocket(),
            ships = arrayListOf(),
            telemetry = Telemetry(),
            launchSite = LaunchSite(),
            launchSuccess = null,
            launchFailureDetails = LaunchFailureDetails(),
            links = Links(),
            details = null,
            staticFireDateUtc = null,
            staticFireDateUnix = null,
            timeline = Timeline(),
            crew = null
        )
        coEvery { allLaunchesService.getAllLaunches() } returns Response.success(listOf(launchModel))
        runBlocking {
            val response = allLaunchesRepository.getAllLaunches()
            Assert.assertEquals(
                "get all launches success test failed",
                launchModel,
                response.data?.get(0)
            )
        }
    }

    @Test
    fun allLaunchErrorTest() {
        coEvery { allLaunchesService.getAllLaunches() } returns Response.error(
            400,
            "{\"key\":[\"error\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        runBlocking {
            val response = allLaunchesRepository.getAllLaunches()
            Assert.assertTrue("All launch error test failed", response is Resource.Error)
        }
    }
}