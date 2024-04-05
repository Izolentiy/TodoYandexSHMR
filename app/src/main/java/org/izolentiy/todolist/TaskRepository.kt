package org.izolentiy.todolist

import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun allTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun addTask(task: Task) = taskDao.addNewTask(task)
}