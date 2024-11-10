package com.gitz.moneyger.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    fun getCurrentFormattedTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
}