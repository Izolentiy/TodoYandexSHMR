package org.izolentiy.todolist.newtask

import android.util.Log
import androidx.lifecycle.ViewModel
import org.izolentiy.todolist.TaskRepository
import javax.inject.Inject

class TaskEditorViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val TAG = TaskEditorViewModel::class.simpleName
    init {
        Log.d(TAG, "CREATED")
    }
}