package com.mindera.rocketscience.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.R
import com.mindera.rocketscience.adapter.HomeAdapter
import com.mindera.rocketscience.adapter.YearFilterAdapter
import com.mindera.rocketscience.databinding.ActivityHomeBinding
import com.mindera.rocketscience.databinding.FilterLayoutBinding
import com.mindera.rocketscience.listeners.FilterListener
import com.mindera.rocketscience.listeners.HomeItemListener
import com.mindera.rocketscience.model.Year
import com.mindera.rocketscience.model.alllaunches.Launch
import com.mindera.rocketscience.util.Extensions.isNetWorkConnected
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeItemListener, FilterListener {

    private lateinit var binding: ActivityHomeBinding
    lateinit var builder: AlertDialog
    lateinit var homeAdapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.homeViewModel = viewModel
        setContentView(binding.root)

        initialize()
    }

    // initialize the observer and making the API call
    private fun initialize() {
        viewModel.updateData.observe(this) {
            binding.isLoading = it
            if (it)
                populateData()
        }
        getData()
        viewModel.networkError.observe(this) {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                .show();
        }
    }

    /* set recycler view adapter for the home screen */
    private fun populateData() {
        homeAdapter = HomeAdapter(viewModel.homeDataModel, this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this);
        binding.recyclerView.adapter = homeAdapter
        homeAdapter.notifyDataSetChanged()
        binding.recyclerView.itemAnimator = null
    }

    // get data from from server
    private fun getData() {
        if (isNetWorkConnected()) {
            viewModel.getCompanyInfo()
        } else {
            viewModel.updateData.postValue(true)
            viewModel.networkError.postValue(getString(R.string.something_went_wrong))
        }
    }

    // TODO update based on requirement
    override fun onCompanyInfoClick() {
    }

    override fun launchItemClick(launchDataModel: Launch) {
        launchDataModel.links?.articleLink?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            showFilterDialog()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    // show the filter dialog on filter button click
    private fun showFilterDialog() {
        var list = viewModel.getYearList(viewModel.allDataModel.launch)
        builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .create()

        val binding: FilterLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.filter_layout,
            null,
            false
        )

        val yearFilterAdapter = YearFilterAdapter(list, this)
        binding.yearRecyclerView.adapter = yearFilterAdapter
        yearFilterAdapter.notifyDataSetChanged()
        binding.clickHandler = this
        builder.setView(binding.root)
        builder.setCanceledOnTouchOutside(true)
        builder.show()
    }

    override fun filterByLaunchSuccess() {
        builder.cancel()
        viewModel.filterLaunchSuccess(viewModel.allDataModel.launch)
        homeAdapter.notifyDataSetChanged()
    }

    override fun filterByAscending() {
        builder.cancel()
        viewModel.filterByAscending(viewModel.allDataModel.launch)
        homeAdapter.notifyDataSetChanged()
    }

    override fun filterByDescending() {
        builder.cancel()
        viewModel.filterByDescending(viewModel.allDataModel.launch)
        homeAdapter.notifyDataSetChanged()
    }

    override fun filterByYearSelected(year: Year) {
        builder.cancel()
        viewModel.filterByYearSelected(year.year, viewModel.allDataModel.launch)
        homeAdapter.notifyDataSetChanged()
    }

    override fun clearFilter() {
        builder.cancel()
        getData()
    }
}