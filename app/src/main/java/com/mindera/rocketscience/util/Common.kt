package com.mindera.rocketscience.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale

object Common {
    const val GIVEN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    const val DATE_FORMAT = "dd/MM/yyyy HH:mm"

    // finding the day difference between two dates
    fun dayDifference(date: String?): Int {
        date?.let {
            val dateFormat = SimpleDateFormat(GIVEN_DATE_FORMAT, Locale.UK)
            val dateFormatted = dateFormat.parse(it) as Date
            val formatter: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

            if (isDateFuture(formatter.format(dateFormatted), DATE_FORMAT)) {
                val start = LocalDate.parse(date, DateTimeFormatter.ofPattern(GIVEN_DATE_FORMAT))
                val dateFormatWithZone = SimpleDateFormat(GIVEN_DATE_FORMAT, Locale.getDefault())
                val currentDate = dateFormatWithZone.format(Date())
                val end =
                    LocalDate.parse(currentDate, DateTimeFormatter.ofPattern(GIVEN_DATE_FORMAT))

                return ChronoUnit.DAYS.between(end, start).toInt()
            } else if (isDatePast(formatter.format(dateFormatted), DATE_FORMAT)) {
                val start = LocalDate.parse(date, DateTimeFormatter.ofPattern(GIVEN_DATE_FORMAT))

                val dateFormatWithZone = SimpleDateFormat(GIVEN_DATE_FORMAT, Locale.getDefault())
                val currentDate = dateFormatWithZone.format(Date())
                val end =
                    LocalDate.parse(currentDate, DateTimeFormatter.ofPattern(GIVEN_DATE_FORMAT))

                return ChronoUnit.DAYS.between(start, end).toInt()
            }
        }
        return 0
    }

    // checking the given date is in past
    fun isDatePast(date: String?, dateFormat: String?): Boolean {
        val localDate: LocalDate = LocalDate.now(ZoneId.systemDefault())
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat)
        val inputDate: LocalDate = LocalDate.parse(date, dtf)
        return inputDate.isBefore(localDate)
    }

    // checking the given date is in future
    fun isDateFuture(date: String?, dateFormat: String?): Boolean {
        val localDate = LocalDate.now(ZoneId.systemDefault())
        val dtf = DateTimeFormatter.ofPattern(dateFormat)
        val inputDate = LocalDate.parse(date, dtf)
        return inputDate.isAfter(localDate)
    }
}