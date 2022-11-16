package com.mindera.rocketscience.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindera.rocketscience.databinding.ItemYearFilterBinding
import com.mindera.rocketscience.listeners.FilterListener
import com.mindera.rocketscience.model.Year


class YearFilterAdapter(
    private val itemList: List<Year>,
    private val filterListener: FilterListener
) :
    RecyclerView.Adapter<YearFilterAdapter.YearItemViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return YearItemViewHolder(
            ItemYearFilterBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: YearItemViewHolder, position: Int) {
        holder.bind(
            itemList[position],
            filterListener = filterListener
        )
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return itemList.size
    }

    // Holds the views for adding the Year
    class YearItemViewHolder(
        private var binding: ItemYearFilterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(year: Year, filterListener: FilterListener) {
            binding.year = year
            binding.clickHandler = filterListener
            binding.executePendingBindings()
        }
    }
}