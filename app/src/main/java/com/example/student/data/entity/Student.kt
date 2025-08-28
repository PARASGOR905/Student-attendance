package com.example.student.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: String,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val className: String? = null,
    val rollNumber: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    companion object {
        const val TABLE_NAME = "students"
    }
}
