package com.mindera.rocketscience.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindera.rocketscience.model.HomeDataModel
import com.mindera.rocketscience.model.Year
import com.mindera.rocketscience.model.alllaunches.Launch
import com.mindera.rocketscience.repository.AllLaunchesRepository
import com.mindera.rocketscience.repository.CompanyInfoRepository
import com.mindera.rocketscience.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val companyInfoRepository: CompanyInfoRepository,
    private val allLaunchesRepository: AllLaunchesRepository
) : ViewModel() {
    var homeDataModel: HomeDataModel = HomeDataModel(null)
    var allDataModel: HomeDataModel = HomeDataModel(null)
    var updateData: MutableLiveData<Boolean> = MutableLiveData()
    var networkError: MutableLiveData<String> = MutableLiveData()
    var yearList: ArrayList<Year> = ArrayList<Year>()

    // making the api call to get the company info
    fun getCompanyInfo() {
        updateData.postValue(false)
        viewModelScope.launch {
            val responseData = companyInfoRepository.getCompanyInfo()
            if (responseData is Resource.Success) {
                responseData.data?.let {
                    launch(Dispatchers.Main) {
                        homeDataModel.companyInfo = it
                        allDataModel.companyInfo = it
                    }
                    getAllLaunches()
                }
            } else {
                updateData.postValue(true)
                // TODO update this approach
                networkError.postValue("Something went wrong")
            }
        }
    }

    // making the api call to get the launch info
    private fun getAllLaunches() {
        viewModelScope.launch {
            val responseData = allLaunchesRepository.getAllLaunches()
            if (responseData is Resource.Success) {
                responseData.data?.let {
                    homeDataModel.launch = it
                    allDataModel.launch = it
                    updateData.postValue(true)
                }
            } else {
                updateData.postValue(true)
                // TODO update this approach
                networkError.postValue("Something went wrong")
            }
        }
    }

    // filter based on launch status
    fun filterLaunchSuccess(unfilteredData: List<Launch>?) {
        homeDataModel.launch = unfilteredData?.filter { it.launchSuccess == true }
    }

    // filter based on year[Descending]
    fun filterByDescending(unfilteredData: List<Launch>?) {
        homeDataModel.launch = unfilteredData?.sortedByDescending { it.launchYear }
    }

    // filter based on year[Ascending]
    fun filterByAscending(unfilteredData: List<Launch>?) {
        homeDataModel.launch = unfilteredData?.sortedBy { it.launchYear }
    }

    // filter based on selected year
    fun filterByYearSelected(year: String, unfilteredData: List<Launch>?) {
        homeDataModel.launch = unfilteredData?.filter {
            it.launchYear == year
        }.also {
            it?.forEach { it.launchYear?.let { yearList.add(Year(it)) } }
        }
    }

    // creating the unique list for filter option
    fun getYearList(unfilteredData: List<Launch>?): List<Year> {
        unfilteredData?.distinctBy { it.launchYear }.also {
            it?.forEach { it.launchYear?.let { yearList.add(Year(it)) } }
        }
        return yearList
    }
}