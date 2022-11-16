package com.mindera.rocketscience

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindera.rocketscience.home.HomeViewModel
import com.mindera.rocketscience.model.alllaunches.*
import com.mindera.rocketscience.model.companyinfo.CompanyInfo
import com.mindera.rocketscience.model.companyinfo.Headquarters
import com.mindera.rocketscience.model.companyinfo.Links
import com.mindera.rocketscience.repository.AllLaunchesRepository
import com.mindera.rocketscience.repository.CompanyInfoRepository
import com.mindera.rocketscience.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @MockK
    var allLaunchesRepository: AllLaunchesRepository = mockk()

    @MockK
    var companyInfoRepository: CompanyInfoRepository = mockk()

    // class under test
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = HomeViewModel(companyInfoRepository, allLaunchesRepository)
    }

    @Test
    fun showProgressTest() {
        Assert.assertTrue(
            "data loading status test failed",
            viewModel.updateData.value != true
        )
    }

    @Test
    fun homeDataModelTest() {
        val companyInfo = CompanyInfo(
            name = "SpaceX",
            founder = null,
            founded = null,
            employees = null,
            vehicles = null,
            launchSites = null,
            testSites = null,
            ceo = null,
            cto = null,
            coo = null,
            ctoPropulsion = null,
            valuation = null,
            headquarters = Headquarters(),
            links = Links(),
            summary = null
        )
        val launchModel = Launch(
            1,
            missionName = "FalconSat",
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
            links = com.mindera.rocketscience.model.alllaunches.Links(),
            details = null,
            staticFireDateUtc = null,
            staticFireDateUnix = null,
            timeline = Timeline(),
            crew = null
        )
        coEvery { allLaunchesRepository.getAllLaunches() } returns Resource.Success(
            listOf(
                launchModel
            )
        )
        coEvery { companyInfoRepository.getCompanyInfo() } returns Resource.Success(companyInfo)

        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getCompanyInfo()
            Assert.assertEquals(
                "HomeDataModel company info test failed" + viewModel.homeDataModel.companyInfo?.name,
                "SpaceX",
                viewModel.homeDataModel.companyInfo?.name
            )

            Assert.assertEquals(
                "HomeDataModel launch test failed",
                "FalconSat",
                viewModel.homeDataModel.launch?.get(0)?.missionName
            )
        }
    }
}