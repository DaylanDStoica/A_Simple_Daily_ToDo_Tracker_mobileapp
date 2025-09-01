package com.example.simple_todo_dailytracker

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.simple_todo_dailytracker.ui.theme.Simple_ToDo_DailyTrackerTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Simple_ToDo_DailyTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    // the files for storage
    private lateinit var DailyTaskListFile : File
    private lateinit var TodaysTaskListFile : File

    // the buttons to change viewModels
    private lateinit var addTaskBttn : Button
    private lateinit var viewTasksBttn: Button
    private lateinit var markProgressBttn: Button

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
//        addTaskBttn.findViewById(R.id.addTaskBttn)
//        viewTasksBttn.findViewById<>()
//        markProgressBttn.findViewById<>()
        // add listeners for buttons clicks to change the view
        addTaskBttn.setOnClickListener {
            // change to addTask viewModel
        }
        viewTasksBttn.setOnClickListener {
            // change to viewing tasks viewModel
        }
        markProgressBttn.setOnClickListener {
            // change to mark progress for today viewModel
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