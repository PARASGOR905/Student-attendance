package com.example.student

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.student.attendance.AttendanceViewModel
import com.example.student.attendance.StudentsScreen
import com.example.student.attendance.TakeAttendanceScreen
import com.example.student.auth.AuthViewModel
import com.example.student.auth.LoginScreen
import com.example.student.auth.UserRole
import com.example.student.ui.DashboardScreen
import com.example.student.ui.HistoryScreen
import com.example.student.ui.theme.StudentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Activity-scoped ViewModels
        val attendanceVm = ViewModelProvider(this)[AttendanceViewModel::class.java]
        val authVm = ViewModelProvider(this)[AuthViewModel::class.java]

        setContent {
            StudentTheme {
                App(authVm, attendanceVm)
            }
        }
    }
}

@Composable
private fun App(
    authVm: AuthViewModel,
    attendanceVm: AttendanceViewModel
) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Login
            composable("login") {
                LoginScreen(vm = authVm) { role: UserRole ->
                    val encoded = Uri.encode(role.name)
                    navController.navigate("dashboard/$encoded") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            // Dashboard with argument
            composable(
                route = "dashboard/{role}",
                arguments = listOf(
                    navArgument("role") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val roleParam = backStackEntry.arguments?.getString("role") ?: UserRole.Student.name
                val role = runCatching { UserRole.valueOf(roleParam) }.getOrDefault(UserRole.Student)

                DashboardScreen(
                    role = role,
                    onGoStudents = { navController.navigate("students") },
                    onGoTakeAttendance = { navController.navigate("take") },
                    onGoHistory = { navController.navigate("history") }
                )
            }

            // Students list
            composable("students") {
                StudentsScreen(vm = attendanceVm) {
                    navController.navigate("take")
                }
            }

            // Take attendance
            composable("take") {
                TakeAttendanceScreen(vm = attendanceVm) {
                    navController.popBackStack()
                }
            }

            // History
            composable("history") {
                HistoryScreen(vm = attendanceVm)
            }
        }
    }
}

fun composable(string: String, function: () -> Unit) {}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    // Works fine because both ViewModels have empty constructors
    StudentTheme {
        MaterialTheme {
            App(
                authVm = AuthViewModel(),
                attendanceVm = AttendanceViewModel()
            )
        }
    }
}
