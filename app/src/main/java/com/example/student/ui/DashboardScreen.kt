package com.example.student.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student.auth.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    role: UserRole,
    onGoStudents: () -> Unit,
    onGoTakeAttendance: () -> Unit,
    onGoHistory: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Dashboard - ${role.name}") })
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (role) {
                UserRole.Student -> {
                    Text("Welcome, Student!", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = onGoHistory) { Text("My Attendance History") }
                }
                UserRole.Teacher, UserRole.Admin -> {
                    Text("Welcome, ${role.name}!", style = MaterialTheme.typography.titleMedium)
                    Button(onClick = onGoStudents) { Text("Manage Students") }
                    Button(onClick = onGoTakeAttendance) { Text("Take Attendance Today") }
                    Button(onClick = onGoHistory) { Text("View Attendance History") }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
