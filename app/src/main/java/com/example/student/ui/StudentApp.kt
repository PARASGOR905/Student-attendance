package com.example.student.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.student.navigation.NavGraph
import com.example.student.ui.theme.StudentTheme

@Composable
fun StudentApp() {
    val navController = rememberNavController()
    
    StudentTheme {
        // Set up the main navigation graph
        NavGraph(navController = navController)
    }
}
