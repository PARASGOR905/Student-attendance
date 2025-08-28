package com.example.student.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    private const val DISPLAY_DATE_FORMAT = "EEEE, MMMM d, yyyy"
    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())
    private val dateTimeFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())

    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }

    fun formatDateForDisplay(dateString: String): String {
        return try {
            val date = dateFormat.parse(dateString) ?: return dateString
            displayDateFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatDateForDatabase(date: Date): String {
        return dateFormat.format(date)
    }

    fun getCurrentDateTime(): String {
        return dateTimeFormat.format(Date())
    }

    fun getDaysBetween(startDate: String, endDate: String): List<String> {
        val dates = mutableListOf<String>()
        try {
            val start = dateFormat.parse(startDate) ?: return emptyList()
            val end = dateFormat.parse(endDate) ?: return emptyList()
            
            val calendar = Calendar.getInstance()
            calendar.time = start
            
            while (!calendar.time.after(end)) {
                dates.add(dateFormat.format(calendar.time))
                calendar.add(Calendar.DATE, 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dates
    }

    fun getStartOfWeek(date: String): String {
        return try {
            val calendar = Calendar.getInstance()
            val parsedDate = dateFormat.parse(date) ?: return date
            calendar.time = parsedDate
            
            // Set to first day of week (Sunday)
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            date
        }
    }

    fun getEndOfWeek(date: String): String {
        return try {
            val calendar = Calendar.getInstance()
            val parsedDate = dateFormat.parse(date) ?: return date
            calendar.time = parsedDate
            
            // Set to last day of week (Saturday)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            dateFormat.format(calendar.time)
        } catch (e: Exception) {
            date
        }
    }
}
