package com.example.student.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.student.attendance.AttendanceViewModel
import com.example.student.attendance.TakeAttendanceScreen
import com.example.student.attendance.StudentsScreen
import com.example.student.auth.AuthViewModel
import com.example.student.auth.LoginScreen
import com.example.student.ui.DashboardScreen
import com.example.student.ui.HistoryScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Auth Screens
        composable(route = Screen.Login.route) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Main Screens
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onViewStudents = { navController.navigate(Screen.Students.route) },
                onViewHistory = { navController.navigate(Screen.History.route) }
            )
        }

        composable(route = Screen.Students.route) {
            val viewModel: AttendanceViewModel = hiltViewModel()
            StudentsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onTakeAttendance = { studentId ->
                    navController.navigate("${Screen.Attendance.route}/$studentId")
                }
            )
        }

        composable(
            route = "${Screen.Attendance.route}/{studentId}",
            arguments = listOf(
                navArgument("studentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel: AttendanceViewModel = hiltViewModel()
            val studentId = backStackEntry.arguments?.getString("studentId") ?: return@composable
            
            TakeAttendanceScreen(
                viewModel = viewModel,
                studentId = studentId,
                onBack = { navController.popBackStack() },
                onAttendanceMarked = { navController.popBackStack() }
            )
        }

        composable(route = Screen.History.route) {
            HistoryScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
