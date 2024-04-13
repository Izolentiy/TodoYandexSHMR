package org.izolentiy.todolist

import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun allTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun getTask(taskId: Long): Task = taskDao.getTask(taskId)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun deleteTask(taskId: Long) = taskDao.deleteTask(taskId)
}