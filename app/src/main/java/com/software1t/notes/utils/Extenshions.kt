package com.software1t.notes.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Extenshions {


    companion object {

        fun getFormattedTimestamp(timeInMillis: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(timeInMillis)
        }

        fun convertDateStringToMillis(dateString: String): Long {
            val lastModifiedFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())
            val currentTimeInMillis = System.currentTimeMillis()
            val formattedTime = lastModifiedFormat.format(Date(currentTimeInMillis))
            val parsedTimeInMillis = lastModifiedFormat.parse(formattedTime)?.time
            return parsedTimeInMillis!!
        }
    }
}