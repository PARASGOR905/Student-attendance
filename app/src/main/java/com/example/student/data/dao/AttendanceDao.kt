package com.example.student.data.dao

import androidx.room.*
import com.example.student.data.entity.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(record: AttendanceRecord): Long

    @Update
    suspend fun updateAttendance(record: AttendanceRecord)

    @Delete
    suspend fun deleteAttendance(record: AttendanceRecord)

    @Query("SELECT * FROM attendance_records WHERE id = :id")
    suspend fun getAttendanceById(id: Long): AttendanceRecord?

    @Query("""
        SELECT * FROM attendance_records 
        WHERE studentId = :studentId 
        ORDER BY date DESC
    """)
    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceRecord>>

    @Query("""
        SELECT * FROM attendance_records 
        WHERE date = :date
    """)
    fun getAttendanceForDate(date: String): Flow<List<AttendanceRecord>>

    @Query("""
        SELECT * FROM attendance_records 
        WHERE studentId = :studentId AND date = :date
    """)
    suspend fun getAttendanceForStudentOnDate(studentId: Long, date: String): AttendanceRecord?

    @Query("""
        SELECT COUNT(*) FROM attendance_records 
        WHERE studentId = :studentId 
        AND status = 'PRESENT'
    """)
    fun getPresentDaysCount(studentId: Long): Flow<Int>

    @Query("""
        SELECT COUNT(*) FROM attendance_records 
        WHERE studentId = :studentId 
        AND status = 'ABSENT'
    """)
    fun getAbsentDaysCount(studentId: Long): Flow<Int>

    @Query("""
        SELECT * FROM attendance_records 
        WHERE date BETWEEN :startDate AND :endDate
        ORDER BY date DESC
    """)
    fun getAttendanceInDateRange(
        startDate: String,
        endDate: String
    ): Flow<List<AttendanceRecord>>

    @Query("""
        DELETE FROM attendance_records 
        WHERE studentId = :studentId
    """)
    suspend fun deleteAllAttendanceForStudent(studentId: Long)

    @Query("""
        SELECT COUNT(DISTINCT date) 
        FROM attendance_records
    """)
    suspend fun getTotalAttendanceDays(): Int

    @Query("""
        SELECT COUNT(*) 
        FROM attendance_records 
        WHERE studentId = :studentId AND status = 'PRESENT'
    """)
    suspend fun getStudentPresentDays(studentId: Long): Int
}
