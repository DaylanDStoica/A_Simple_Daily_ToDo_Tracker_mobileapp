package com.example.simple_todo_dailytracker

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simple_todo_dailytracker.ui.theme.Simple_ToDo_DailyTrackerTheme
import java.io.File

// Add these imports for navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private lateinit var dailyTaskListFile : File
    private lateinit var todaysTaskListFile : File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Simple_ToDo_DailyTrackerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("main") {
                            MainScreen(
                                onAddTask = { navController.navigate("addTask") },
                                onViewTasks = { navController.navigate("viewTasks") },
                                onMarkProgress = { navController.navigate("markProgress") }
                            )
                        }
                        composable("addTask") { AddTaskScreen(onBack = { navController.popBackStack() }) }
                        composable("viewTasks") { ViewTasksScreen(onBack = { navController.popBackStack() }) }
                        composable("markProgress") { MarkProgressScreen(onBack = { navController.popBackStack() }) }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onAddTask: () -> Unit,
    onViewTasks: () -> Unit,
    onMarkProgress: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Greeting(name = "User")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddTask) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onViewTasks) {
            Text("View Tasks")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onMarkProgress) {
            Text("Mark Progress")
        }
    }
}

// screens for the different features of the app:
// 1. AddTask, with a keyboard
// 2. ViewTask, read only as a reminder
// 3. MarkProgress, with a slider or a checkmark to signify task complete
@Composable
fun AddTaskScreen(onBack: () -> Unit) {
    // screen for adding a task to the list
    var taskName by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add Task Screen") // task at top of screen, identifier
        Spacer(Modifier.height(8.dp))
        TextField( // text input
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") }
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = { // button for submitting the task to be added
            // Use taskName here (e.g., save the task)

            Log.d("AddTaskScreen", "Task entered: $taskName")
            // You can add your save logic here
        }) {
            Text("Submit Task")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun ViewTasksScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("View Tasks Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun MarkProgressScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Mark Progress Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name! \n Ready to track your tasks for the day",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Simple_ToDo_DailyTrackerTheme {
        MainScreen({}, {}, {})
    }
}
