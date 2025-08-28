package com.example.student.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "attendance_records",
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("studentId"),
        Index(["date", "studentId"], unique = true)
    ]
)
data class AttendanceRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val status: AttendanceStatus = AttendanceStatus.PRESENT,
    val markedAt: Long = System.currentTimeMillis(),
    val notes: String? = null
) {
    enum class AttendanceStatus {
        PRESENT,
        ABSENT,
        LATE,
        EXCUSED
    }

    companion object {
        const val TABLE_NAME = "attendance_records"
    }
}
