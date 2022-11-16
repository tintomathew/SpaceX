package com.mindera.rocketscience.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.mindera.rocketscience.R
import com.mindera.rocketscience.util.Common.GIVEN_DATE_FORMAT
import com.squareup.picasso.Picasso
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Extensions {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            // Load the image in the background using Picasso.
            Picasso.with(imageView.context).load(imgUrl).fit().centerCrop()
                .placeholder(R.drawable.error_sing)
                .error(R.drawable.error_sing)
                .into(imageView);
        }
    }

    fun Context.isNetWorkConnected(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager
            .getNetworkCapabilities(network)
        return (capabilities != null
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
    }

    fun String.toDate(
        dateFormat: String = "yyyy-MM-dd HH:mm:ss",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date? {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    @JvmStatic
    @BindingAdapter("dateText")
    fun TextView.setDateText(date: String?) {
        date?.let {
            val dateFormat = SimpleDateFormat(GIVEN_DATE_FORMAT)
            val dateFormatted = dateFormat.parse(it)
            val formatter: DateFormat =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val formatterTime: DateFormat =
                SimpleDateFormat("HH:mm", Locale.getDefault())

            formatter.timeZone = TimeZone.getDefault()
            text = dateFormatted?.let {
                this.context.getString(
                    R.string.date_time,
                    formatter.format(it),
                    formatterTime.format(it)
                )
            }
        }
    }
}