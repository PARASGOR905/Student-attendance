package com.example.student.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeAttendanceScreen(
    vm: AttendanceViewModel,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Take Attendance") })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Present: ${vm.presentCountToday()} / ${vm.students.size}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = onBack) { Text("Done") }
        }
        Divider()
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(vm.students, key = { it.id }) { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(student.name, style = MaterialTheme.typography.bodyLarge)
                    val checked = vm.isPresentToday(student.id)
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { vm.setPresentToday(student.id, it) }
                    )
                }
                Divider()
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
    }
}
