package org.izolentiy.todolist.tasklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.izolentiy.todolist.datasource.TaskRepository
import org.izolentiy.todolist.tasklist.TaskSort.Type
import org.izolentiy.todolist.model.Task
import javax.inject.Inject

class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val TAG = TaskListViewModel::class.simpleName

    init {
        Log.d(TAG, "CREATED")
    }

    private val sortTask = MutableStateFlow(TaskSort(Type.BY_STATUS, isAscending = true))
    val currentSort get() = sortTask.value
    @OptIn(ExperimentalCoroutinesApi::class)
    val taskList: Flow<List<Task>> = sortTask
        .flatMapLatest { taskRepository.getSortedTasks(it) }

    fun applySort(sort: TaskSort) {
        Log.d(TAG, "applySort: NEW SORT APPLIED")
        Log.d(TAG, "applySort: $currentSort")
        sortTask.value = sort
        Log.d(TAG, "applySort: $currentSort")
    }

    fun updateTask(task: Task): Job = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: CLEARED")
        super.onCleared()
    }
}