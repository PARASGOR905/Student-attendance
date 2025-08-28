package com.example.student.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student.data.dao.StudentDao
import com.example.student.data.dao.AttendanceDao
import com.example.student.data.entity.Student
import com.example.student.data.entity.AttendanceRecord

@Database(
    entities = [
        Student::class,
        AttendanceRecord::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                
                INSTANCE = instance
                instance
            }
        }

        // For testing
        @VisibleForTesting
        fun setTestInstance(database: AppDatabase) {
            INSTANCE = database
        }
    }
}
