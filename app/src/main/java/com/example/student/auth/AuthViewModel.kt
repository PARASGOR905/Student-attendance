package com.example.student.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class UserRole { Student, Teacher, Admin }

class AuthViewModel : ViewModel() {
    var selectedRole by mutableStateOf(UserRole.Student)
        private set

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var error: String? by mutableStateOf(null)
        private set

    fun onRoleChange(role: UserRole) { selectedRole = role; error = null }
    fun onUsernameChange(v: String) { username = v }
    fun onPasswordChange(v: String) { password = v }

    fun login(onLoggedIn: (UserRole) -> Unit) {
        error = null
        val u = username.trim()
        val p = password
        when (selectedRole) {
            UserRole.Student -> {
                if (u.isEmpty()) { error = "Enter a username"; return }
                onLoggedIn(UserRole.Student)
            }
            UserRole.Teacher -> {
                if (u == "teacher" && p == "1234") onLoggedIn(UserRole.Teacher)
                else error = "Invalid teacher credentials"
            }
            UserRole.Admin -> {
                if (u == "admin" && p == "admin") onLoggedIn(UserRole.Admin)
                else error = "Invalid admin credentials"
            }
        }
    }
}
