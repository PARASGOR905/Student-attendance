package com.example.student.data.repository

import com.example.student.data.dao.AttendanceDao
import com.example.student.data.entity.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val attendanceDao: AttendanceDao
) {
    suspend fun markAttendance(record: AttendanceRecord): Long {
        return attendanceDao.insertAttendance(record)
    }

    suspend fun updateAttendance(record: AttendanceRecord) {
        attendanceDao.updateAttendance(record)
    }

    suspend fun deleteAttendance(record: AttendanceRecord) {
        attendanceDao.deleteAttendance(record)
    }

    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceRecord>> {
        return attendanceDao.getAttendanceForStudent(studentId)
    }

    fun getAttendanceForDate(date: String): Flow<List<AttendanceRecord>> {
        return attendanceDao.getAttendanceForDate(date)
    }

    suspend fun getAttendanceForStudentOnDate(studentId: Long, date: String): AttendanceRecord? {
        return attendanceDao.getAttendanceForStudentOnDate(studentId, date)
    }

    fun getPresentDaysCount(studentId: Long): Flow<Int> {
        return attendanceDao.getPresentDaysCount(studentId)
    }

    fun getAbsentDaysCount(studentId: Long): Flow<Int> {
        return attendanceDao.getAbsentDaysCount(studentId)
    }

    fun getAttendanceInDateRange(
        startDate: String,
        endDate: String
    ): Flow<List<AttendanceRecord>> {
        return attendanceDao.getAttendanceInDateRange(startDate, endDate)
    }

    suspend fun deleteAllAttendanceForStudent(studentId: Long) {
        attendanceDao.deleteAllAttendanceForStudent(studentId)
    }

    suspend fun getTotalAttendanceDays(): Int {
        return attendanceDao.getTotalAttendanceDays()
    }

    suspend fun getStudentPresentDays(studentId: Long): Int {
        return attendanceDao.getStudentPresentDays(studentId)
    }

    suspend fun calculateAttendancePercentage(studentId: Long): Float {
        val presentDays = getStudentPresentDays(studentId).toFloat()
        val totalDays = getTotalAttendanceDays().toFloat()
        return if (totalDays > 0) (presentDays / totalDays) * 100 else 0f
    }
}
