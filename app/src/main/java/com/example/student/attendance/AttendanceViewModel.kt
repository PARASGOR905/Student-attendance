package com.example.student.attendance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Student(val id: Int, val name: String)

data class AttendanceDay(
    val dateKey: String,
    val presentIds: Set<Int>
)

class AttendanceViewModel : ViewModel() {
    private var nextId = 1

    // List of students
    val students = mutableStateListOf<Student>()

    // Map of dateKey -> set of present student ids
    private val attendanceMap = mutableMapOf<String, MutableSet<Int>>()

    var newStudentName by mutableStateOf("")
        private set

    fun onNewStudentNameChange(value: String) {
        newStudentName = value
    }

    fun addStudent() {
        val name = newStudentName.trim()
        if (name.isNotEmpty()) {
            students.add(Student(nextId++, name))
            newStudentName = ""
        }
    }

    fun removeStudent(id: Int) {
        students.removeAll { it.id == id }
        // Also remove from all attendance sets
        attendanceMap.values.forEach { it.remove(id) }
    }

    private fun todayKey(): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    fun isPresentToday(id: Int): Boolean {
        val key = todayKey()
        return attendanceMap[key]?.contains(id) == true
    }

    fun setPresentToday(id: Int, present: Boolean) {
        val key = todayKey()
        val set = attendanceMap.getOrPut(key) { mutableSetOf() }
        if (present) set.add(id) else set.remove(id)
    }

    fun presentCountToday(): Int {
        val key = todayKey()
        return attendanceMap[key]?.size ?: 0
    }

    // History helpers
    fun historyDays(): List<AttendanceDay> = attendanceMap
        .entries
        .sortedByDescending { it.key }
        .map { AttendanceDay(it.key, it.value.toSet()) }

    fun nameFor(id: Int): String = students.firstOrNull { it.id == id }?.name ?: "Unknown"
}
