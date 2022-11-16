package com.mindera.rocketscience.adapter

import android.content.Context
import android.icu.math.BigDecimal
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.ItemCompanyInfoBinding
import com.mindera.rocketscience.databinding.ItemLaunchBinding
import com.mindera.rocketscience.listeners.HomeItemListener
import com.mindera.rocketscience.model.HomeDataModel
import com.mindera.rocketscience.model.alllaunches.Launch
import com.mindera.rocketscience.model.companyinfo.CompanyInfo
import com.mindera.rocketscience.util.Common
import com.mindera.rocketscience.util.Common.isDateFuture
import com.mindera.rocketscience.util.Common.isDatePast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale


class HomeAdapter(
    private val itemList: HomeDataModel,
    private val homeItemListener: HomeItemListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class HomeViewType {
        COMPANY_INFO,
        LAUNCH_ITEM
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            HomeViewType.LAUNCH_ITEM.ordinal -> return LaunchViewHolder(
                ItemLaunchBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> return InfoViewHolder(
                ItemCompanyInfoBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                itemList.companyInfo?.let {
                    (holder as InfoViewHolder).bind(
                        createCompanyInfoString(holder.itemView.context, it),
                        homeItemListener = homeItemListener
                    )
                }
            }
            else -> {
                (holder as LaunchViewHolder).bind(
                    itemList.launch?.get(position - 1),
                    homeItemListener = homeItemListener,
                    dayDifference = createNoOfDayString(
                        holder.itemView.context,
                        itemList.launch?.get(position - 1)?.launchDateUtc.toString()
                    )
                )
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return itemList.launch?.size?.plus(1) ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HomeViewType.COMPANY_INFO.ordinal
            else -> HomeViewType.LAUNCH_ITEM.ordinal
        }
    }

    // Holds the views for adding the CakeDataModel
    class LaunchViewHolder(
        private var binding: ItemLaunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            launchDataModel: Launch?,
            homeItemListener: HomeItemListener,
            dayDifference: String
        ) {
            binding.launchDataModel = launchDataModel
            binding.clickLaunchHandler = homeItemListener
            binding.dayDifference = dayDifference
            binding.executePendingBindings()
        }
    }

    class InfoViewHolder(
        private var binding: ItemCompanyInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(companyInfoString: String, homeItemListener: HomeItemListener) {
            binding.companyInfoString = companyInfoString
            binding.clickHandler = homeItemListener
            binding.executePendingBindings()
        }
    }

    // creating the company information string based on server data
    private fun createCompanyInfoString(
        ctx: Context,
        companyInfoDataModel: CompanyInfo
    ): String {
        return ctx.getString(
            R.string.company_info,
            companyInfoDataModel.name.toString(),
            companyInfoDataModel.founder.toString(),
            companyInfoDataModel.founded.toString(),
            companyInfoDataModel.employees.toString(),
            companyInfoDataModel.valuation?.let { BigDecimal(it).toString() }
        )
    }

    // calculate the day difference between current date and launch date
    private fun createNoOfDayString(context: Context, launchDate: String): String {
        val dateFormat = SimpleDateFormat(Common.GIVEN_DATE_FORMAT)
        val dateFormatted = dateFormat.parse(launchDate)
        val formatter: DateFormat = SimpleDateFormat(Common.DATE_FORMAT, Locale.getDefault())
        val formattedDate = dateFormatted?.let { formatter.format(it) }
        return when {
            isDatePast(formattedDate, Common.DATE_FORMAT) -> {
                context.getString(
                    R.string.no_of_days_negative,
                    Common.dayDifference(launchDate).toString()
                )
            }
            isDateFuture(formattedDate, Common.DATE_FORMAT) -> {
                context.getString(
                    R.string.no_of_days_positive,
                    Common.dayDifference(launchDate).toString()
                )
            }
            else -> context.getString(R.string.zero_days)
        }

    }
}