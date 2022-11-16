package com.mindera.rocketscience

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mindera.rocketscience.model.companyinfo.CompanyInfo
import com.mindera.rocketscience.model.companyinfo.Headquarters
import com.mindera.rocketscience.model.companyinfo.Links
import com.mindera.rocketscience.repository.CompanyInfoRepository
import com.mindera.rocketscience.service.CompanyInfoService
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
class CompanyInfoRepositoryTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    var companyInfoService: CompanyInfoService = mockk()

    // class under test
    lateinit var companyInfoRepository: CompanyInfoRepository

    @Before
    fun setup() {
        companyInfoRepository = CompanyInfoRepository(companyInfoService)
    }

    @Test
    fun companyInfoSuccessTest() {
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
        coEvery { companyInfoService.getComapnyInfo() } returns Response.success(companyInfo)
        runBlocking {
            val response = companyInfoRepository.getCompanyInfo()
            Assert.assertEquals("companyInfo success test failed", "SpaceX", response.data?.name)
        }
    }

    @Test
    fun companyInfoErrorTest() {
        coEvery { companyInfoService.getComapnyInfo() } returns Response.error(
            400,
            "{\"key\":[\"error\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())
        )
        runBlocking {
            val response = companyInfoRepository.getCompanyInfo()
            Assert.assertTrue("companyInfo error test failed", response is Resource.Error)
        }
    }
}