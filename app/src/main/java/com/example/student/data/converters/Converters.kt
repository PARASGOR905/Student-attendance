package com.example.student.data.converters

import androidx.room.TypeConverter
import com.example.student.data.entity.AttendanceRecord
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let { dateFormat.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun fromAttendanceStatus(status: AttendanceRecord.AttendanceStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toAttendanceStatus(value: String?): AttendanceRecord.AttendanceStatus? {
        return value?.let { enumValueOf<AttendanceRecord.AttendanceStatus>(it) }
    }
}
