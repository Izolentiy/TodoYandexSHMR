package org.izolentiy.todolist.taskeditor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izolentiy.todolist.TaskRepository
import org.izolentiy.todolist.model.Task
import java.util.Date
import javax.inject.Inject

class TaskEditorViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val TAG = TaskEditorViewModel::class.simpleName
    init {
        Log.d(TAG, "$TAG CREATED")
    }

    private val _taskState = MutableStateFlow(Task())
    val taskState get() = _taskState.asStateFlow()
    var task
        get() = taskState.value
        private set(value) { _taskState.value = value }
    var savedTask = task
        private set(value) { field = value }

    fun loadTask(taskId: Long?): Job = viewModelScope.launch {
        if (taskId == null || taskId == task.id) return@launch
        task = taskRepository.getTask(taskId)
        savedTask = task
    }

    fun saveTask(): Job = viewModelScope.launch {
        task = task.copy(id = taskRepository.insertTask(task))
        savedTask = task
    }

    fun deleteTask(): Job = viewModelScope.launch {
        taskRepository.deleteTask(task.id)
    }

    fun updateDeadline(deadline: Date?) {
        task = task.copy(deadline = deadline)
    }

    fun updateText(text: String) {
        task = task.copy(text = text)
    }

    fun updatePriority(priority: Task.Priority) {
        task = task.copy(priority = priority)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: $TAG CLEARED")
        super.onCleared()
    }
}