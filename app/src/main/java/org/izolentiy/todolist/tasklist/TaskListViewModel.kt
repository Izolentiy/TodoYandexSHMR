package org.izolentiy.todolist.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.izolentiy.todolist.TaskRepository
import org.izolentiy.todolist.model.Task
import java.util.Date
import javax.inject.Inject

class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val TAG = TaskListViewModel::class.simpleName

    init {
        Log.d(TAG, "CREATED")
    }

    val taskList: Flow<List<Task>> = taskRepository.allTasks()

    fun addNewTask() {
        viewModelScope.launch {
            val task = Task(
                id = System.currentTimeMillis().toString(),
                text = "Buy something",
                priority = Task.Priority.BASIC,
                deadline = null,
                isDone = false,
                created = Date(System.currentTimeMillis()),
                changed = Date(System.currentTimeMillis())
            )
            taskRepository.addTask(task)
        }
    }
}