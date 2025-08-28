package com.example.student.di

import android.content.Context
import com.example.student.data.AppDatabase
import com.example.student.data.dao.AttendanceDao
import com.example.student.data.dao.StudentDao
import com.example.student.data.repository.AttendanceRepository
import com.example.student.data.repository.StudentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideStudentDao(database: AppDatabase): StudentDao {
        return database.studentDao()
    }

    @Provides
    fun provideAttendanceDao(database: AppDatabase): AttendanceDao {
        return database.attendanceDao()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        studentDao: StudentDao
    ): StudentRepository {
        return StudentRepository(studentDao)
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(
        attendanceDao: AttendanceDao
    ): AttendanceRepository {
        return AttendanceRepository(attendanceDao)
    }
}
