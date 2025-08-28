package com.example.student.navigation

sealed class Screen(val route: String) {
    // Auth
    object Login : Screen("login")
    
    // Main
    object Dashboard : Screen("dashboard")
    object Students : Screen("students")
    object Attendance : Screen("attendance") {
        const val ROUTE_WITH_ARGS = "attendance/{studentId}"
    }
    object History : Screen("history")
    
    companion object {
        const val STUDENT_ID_ARG = "studentId"
    }
}
