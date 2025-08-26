package com.example.student.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.student.attendance.AttendanceViewModel
import com.example.student.attendance.AttendanceDay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(vm: AttendanceViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Attendance History") })
        val days: List<AttendanceDay> = vm.historyDays()
        if (days.isEmpty()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("No history yet", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(days) { day ->
                    val names = day.presentIds.map { vm.nameFor(it) }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(day.dateKey, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Present: ${names.size} ${if (names.isNotEmpty()) "- " + names.joinToString() else ""}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
