package com.example.simple_todo_dailytracker

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simple_todo_dailytracker.ui.theme.Simple_ToDo_DailyTrackerTheme
import java.io.File



class MainActivity : ComponentActivity() {
    // the files for storage
    private lateinit var dailyTaskListFile : File
    private lateinit var todaysTaskListFile : File

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Simple_ToDo_DailyTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                        Greeting(name = "Android")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { /* TODO: Add Task logic */ }) {
                            Text("Add Task")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { /* TODO: View Tasks logic */ }) {
                            Text("View Tasks")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { /* TODO: Mark Progress logic */ }) {
                            Text("Mark Progress")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Simple_ToDo_DailyTrackerTheme {
        Greeting("Android")
    }
}
