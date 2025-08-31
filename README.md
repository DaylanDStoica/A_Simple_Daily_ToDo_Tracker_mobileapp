# A_Simple_Daily_ToDo_Tracker_mobileapp
a mobile application version of  A-Simple-Daily-ToDo-Tracker web application.

# A-Simple-Daily-ToDo-Tracker
Tracker for Daily tasks, lists are entirely text-based for user discretion

## Goal:
many digital daily task trackers are too complicated for ease of use
This will have the complexity of a notepad and pen, that resets to your base task list every day.

## Interface:
homepage will consist of 3 buttons:
1. Add Task
2. Check Progress
3. Mark Progress

each button will load the relevant page
1. Add Task will take to a page, that will take the textarea input, and add to the permanent dailytasklist.txt
2. Check Progress will take to the current day's tasks and progress for those tasks. Read only.
3. Mark Progress will write to the entry within the day's task list. When a task is considered complete, the task will be flagged as such until the next day.

### File Structure

There will be a permanent daily list file (dailytasklist.txt) that users will write to
Today's task list ( todaystasklist.txt) that resets so its contents match the contents of the above permanent tracker list. This is where the day's progress is tracked.
At a defined time each day, the day's task list will perform its reset routine.

### Example of Daily Tasks
Walk X miles every day
Drnk Y liters of Water

## Features
Allow incremental progress of the tasks ( "Drank 0.75 liters, totaling 1.2 L out of the days Y liters")
