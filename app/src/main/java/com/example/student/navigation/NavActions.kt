package com.example.student.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * A class that holds navigation actions for the app.
 * This provides a type-safe way to navigate between screens.
 */
class NavActions(private val navController: NavHostController) {
    
    // Navigation actions for the auth flow
    object Auth {
        const val LOGIN = "auth/login"
        const val REGISTER = "auth/register"
        const val FORGOT_PASSWORD = "auth/forgot-password"
    }
    
    // Navigation actions for the main app
    object Main {
        const val DASHBOARD = "main/dashboard"
        const val STUDENTS_LIST = "main/students"
        const val STUDENT_DETAIL = "main/student/{studentId}"
        const val ATTENDANCE = "main/attendance"
        const val REPORTS = "main/reports"
        const val PROFILE = "main/profile"
        const val SETTINGS = "main/settings"
    }
    
    // Navigation actions for dialogs
    object Dialog {
        const val CONFIRM_LOGOUT = "dialog/confirm-logout"
        const val DELETE_CONFIRMATION = "dialog/delete-confirmation"
    }
    
    // Navigation actions for bottom sheet
    object BottomSheet {
        const val FILTER_OPTIONS = "bottomsheet/filter-options"
    }
    
    // Navigation methods
    fun navigate(route: String) {
        navController.navigate(route)
    }
    
    fun navigateAndClearBackStack(route: String) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
        }
    }
    
    fun navigateWithArgs(route: String, vararg args: Pair<String, Any>) {
        navController.navigate(route) {
            args.forEach { (key, value) ->
                navController.currentBackStackEntry?.arguments?.putString(key, value.toString())
            }
        }
    }
    
    fun navigateUp() {
        navController.navigateUp()
    }
    
    fun popBackStack() {
        navController.popBackStack()
    }
    
    fun popBackStack(route: String, inclusive: Boolean = false) {
        navController.popBackStack(route, inclusive)
    }
    
    // Helper functions for specific navigation cases
    fun navigateToLogin() = navigateAndClearBackStack(Auth.LOGIN)
    
    fun navigateToDashboard() = navigateAndClearBackStack(Main.DASHBOARD)
    
    fun navigateToStudentDetail(studentId: String) {
        navigate("${Main.STUDENT_DETAIL.replace("{studentId}", studentId)}")
    }
    
    fun showConfirmLogoutDialog() = navigate(Dialog.CONFIRM_LOGOUT)
    
    fun showDeleteConfirmationDialog() = navigate(Dialog.DELETE_CONFIRMATION)
    
    fun showFilterOptions() = navigate(BottomSheet.FILTER_OPTIONS)
    
    // Helper function to get arguments
    fun getStringArg(key: String): String? {
        return navController.previousBackStackEntry?.arguments?.getString(key)
    }
    
    fun getIntArg(key: String, defaultValue: Int = 0): Int {
        return navController.previousBackStackEntry?.arguments?.getInt(key, defaultValue) ?: defaultValue
    }
    
    fun getBooleanArg(key: String, defaultValue: Boolean = false): Boolean {
        return navController.previousBackStackEntry?.arguments?.getBoolean(key, defaultValue) ?: defaultValue
    }
}

/**
 * A composable function to remember and provide NavActions
 */
@Composable
fun rememberNavActions(navController: NavHostController = rememberNavController()): NavActions {
    return remember(navController) {
        NavActions(navController)
    }
}
