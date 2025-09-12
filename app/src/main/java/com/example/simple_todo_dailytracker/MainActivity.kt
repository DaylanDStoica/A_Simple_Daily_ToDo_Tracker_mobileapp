package com.example.simple_todo_dailytracker

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext

// Add these imports for navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.io.IOException
import java.io.InputStream // commenting this import out, results in blank space to be added to storage file when being written to.



const val DAILYTASKLISTSFILE : String = "dailyTaskFile.txt"
const val TODAYSTASKLISTFILE : String = "todaysTaskFile.txt"

// function to reset contents of TODAYSTASKLISTFILE to be the contents of dailyTaskFile
fun ResetTodaysTasks(context: Context) {
    try {
        val dailyTasks = File(context.filesDir, DAILYTASKLISTSFILE)
        val todaysTasks = File(context.filesDir, TODAYSTASKLISTFILE)
        if (dailyTasks.exists()) {
            val content = dailyTasks.readText()
            todaysTasks.writeText(content)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
class MainActivity : ComponentActivity() {



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
                                onMarkProgress = { navController.navigate("markProgress") },
                                onRemoveTask = { navController.navigate("removeTask")}
                            )
                        }
                        composable("addTask") { AddTaskScreen(onBack = { navController.popBackStack() }) }
                        composable("viewTasks") { ViewTasksScreen(onBack = { navController.popBackStack() }) }
                        composable("markProgress") { MarkProgressScreen(onBack = { navController.popBackStack() }) }
                        composable("removeTask") { RemoveTaskScreen( onBack = { navController.popBackStack() }) }
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
    onMarkProgress: () -> Unit,
    onRemoveTask: () -> Unit
) {
    val context = LocalContext.current
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
        Spacer(modifier = Modifier.height(8.dp))
        // TODO: change ResetTasks button to one with a confirmation window. One click to reset a day's progress is too easy to happen by accident.
        Button( onClick = { ResetTodaysTasks(context = context) }){
            Text("Reset Daily Tasks")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRemoveTask) {
            Text("Remove Daily Task")
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
    val context = LocalContext.current // <-- Move this here!
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Add Task Screen") // task at top of screen, identifier
        Spacer(Modifier.height(8.dp))
        TextField( // text input
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") }
        )
        Spacer(Modifier.height(8.dp))
        // have the two buttons in a row: 'Submit Daily Task' and 'Submit Today's Task'
        // DailyTask will be added to both storage tasklist files, and TodaysTask will be only to TodaysTAskList
        Row{
            Button(onClick = { // button to submit task to end of the DAILYTASKLISTSFILE
                Log.d("AddTaskScreen", "Daily Task entered: $taskName")
                // writing to internal storage
                context.openFileOutput(DAILYTASKLISTSFILE, Context.MODE_APPEND).use { output ->
                    output.write((taskName + "\n").toByteArray())
                }
                context.openFileOutput(TODAYSTASKLISTFILE, Context.MODE_APPEND).use { output ->
                    output.write((taskName + "\n").toByteArray())
                }
                // after writing to the file, clear the text value in the box
//            Text("Task added: $taskName")
                taskName = "" // set visible task entry box to empty
                // display a message telling that the task was added
            }) {
                Text("Submit Daily Task")
            }
            Button( onClick = { // add task for only for today, will disappear when reset
                Log.d("AddTaskScreen", "Today Task entered: $taskName")
                context.openFileOutput(TODAYSTASKLISTFILE, Context.MODE_APPEND).use { output ->
                    output.write((taskName + "\n").toByteArray())
                }
                // after writing to the file, clear the text value in the box
//            Text("Task added: $taskName")
                taskName = "" // set visible task entry box to empty
                // display a message telling that the task was added
            }) {
                Text( "Submit Today Task")
            }
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun ViewTasksScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var tasks by remember { mutableStateOf("") }

    // Read the file when the composable is first composed
    LaunchedEffect(Unit) {
        try {
            // reading from internal storage
            context.openFileInput(TODAYSTASKLISTFILE).use { input ->
                tasks = input.bufferedReader().readText()
            }
        } catch (e: IOException) {
            tasks = "No tasks found."
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("View Tasks Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Text(tasks)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }
}

@Composable
fun MarkProgressScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var tasks by remember { mutableStateOf(listOf<String>()) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingText by remember { mutableStateOf("") }

    // Read tasks from file on first composition
    LaunchedEffect(Unit) {
        try {
            context.openFileInput(TODAYSTASKLISTFILE).use { input ->
                tasks = input.bufferedReader().readLines()
            }
        } catch (e: IOException) {
            tasks = listOf("No tasks found.")
        }
    }

    // Function to update a task and save to file
    fun updateTask(index: Int, newTask: String) {
        val updatedTasks = tasks.toMutableList()
        updatedTasks[index] = newTask
        tasks = updatedTasks
        // Write all tasks back to file
        context.openFileOutput(TODAYSTASKLISTFILE, Context.MODE_PRIVATE).use { output ->
            output.write(updatedTasks.joinToString("\n").toByteArray())
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Mark Progress Screen")
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEachIndexed { idx, task ->
            Button(
                onClick = {
                    editingIndex = idx
                    editingText = task
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(task)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }

    // Edit dialog
    if (editingIndex != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { editingIndex = null },
            title = { Text("Edit Task") },
            text = {
                TextField(
                    value = editingText,
                    onValueChange = { editingText = it },
                    label = { Text("Task") }
                )
            },
            confirmButton = {
                Row { // Use a Row inside confirmButton to show both "Save" and "Complete Task" actions.
                    Button(onClick = {
                        editingIndex?.let { idx ->
                            updateTask(idx, editingText)
                        }
                        editingIndex = null
                    }) { Text("Save") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        editingIndex?.let { idx ->
                            // Remove the selected task
                            val updatedTasks = tasks.toMutableList().apply { removeAt(idx) }
                            tasks = updatedTasks
                            // Write the updated list back to the file (no empty lines)
                            context.openFileOutput(TODAYSTASKLISTFILE, Context.MODE_PRIVATE).use { output ->
                                output.write(updatedTasks.joinToString("\n").toByteArray())
                            }
                        }
                        editingIndex = null // close dialog after completing the rewrite
                    }) { Text("Complete Task") }
                }
            },
            dismissButton = {
                Button(onClick = { editingIndex = null }) { Text("Cancel") }
            }
        )
    }
}

// TODO: setup the RemoveTask Screen for removing lines from the DailyTasks File
@Composable
fun RemoveTaskScreen(onBack: () -> Unit) {
    // screen for removing tasks from permanent DailyTasks
    val context = LocalContext.current
    var tasks by remember { mutableStateOf(listOf<String>()) }
    var editingIndex by remember { mutableStateOf<Int?>(null) }
    var editingText by remember { mutableStateOf("") }

    // Read tasks from file on first composition
    LaunchedEffect(Unit) {
        try {
            context.openFileInput(DAILYTASKLISTSFILE).use { input ->
                tasks = input.bufferedReader().readLines()
            }
        } catch (e: IOException) {
            tasks = listOf("No tasks found.")
        }
    }

    // Function to update a task and save to file
    fun updateTask(index: Int, newTask: String) {
        val updatedTasks = tasks.toMutableList()
        updatedTasks[index] = newTask
        tasks = updatedTasks
        // Write all tasks back to file
        context.openFileOutput(DAILYTASKLISTSFILE, Context.MODE_PRIVATE).use { output ->
            output.write(updatedTasks.joinToString("\n").toByteArray())
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Remove Task Screen")
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEachIndexed { idx, task ->
            Button(
                onClick = {
                    editingIndex = idx
                    editingText = task
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(task)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Back") }
    }

    // Edit dialog
    if (editingIndex != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { editingIndex = null },
            title = { Text("Edit or Remove Task") },
            text = {
                TextField(
                    value = editingText,
                    onValueChange = { editingText = it },
                    label = { Text("Task") }
                )
            },
            confirmButton = {
                Row {
                    Button(onClick = {
                        editingIndex?.let { idx ->
                            updateTask(idx, editingText)
                        }
                        editingIndex = null
                    }) { Text("Save") }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = {
                        editingIndex?.let { idx ->
                            // Remove the selected task
                            val updatedTasks = tasks.toMutableList().apply { removeAt(idx) }
                            tasks = updatedTasks
                            // Write the updated list back to the file (no empty lines)
                            context.openFileOutput(DAILYTASKLISTSFILE, Context.MODE_PRIVATE).use { output ->
                                output.write(updatedTasks.joinToString("\n").toByteArray())
                            }
                        }
                        editingIndex = null
                    }) { Text("Remove Task") }
                }
            },
            dismissButton = {
                Button(onClick = { editingIndex = null }) { Text("Cancel") }
            }
        )
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
        MainScreen({}, {}, {}, onRemoveTask = {} )
    }
}

// TODO: develop a screen for writing tasks that only need to be done once, rather than daily
// user should still be able to view all tasks under the single ViewTasks screen and edit as such