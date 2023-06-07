package com.software1t.notes.utils

import java.text.SimpleDateFormat
import java.util.Locale

class Extenshions {


    companion object {

        fun getFormattedTimestamp(timeInMillis: Long): String {
            val dateFormat = SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(timeInMillis)
        }
    }
}