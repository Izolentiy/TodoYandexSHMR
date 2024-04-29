package org.izolentiy.todolist.datasource

import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task
import org.izolentiy.todolist.tasklist.TaskSort
import org.izolentiy.todolist.tasklist.TaskSort.Type
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getSortedTasks(sort: TaskSort): Flow<List<Task>> {
        return when (sort.type) {
            Type.BY_DATE_CREATED -> taskDao.getSortedByDateCreated(sort.isAscending)
            Type.BY_DATE_MODIFIED -> taskDao.getSortedByDateModified(sort.isAscending)
            Type.BY_DEADLINE -> taskDao.getSortedByDeadline(sort.isAscending)
            Type.BY_IMPORTANCE -> taskDao.getSortedByImportance(sort.isAscending)
            Type.BY_STATUS -> taskDao.getSortedByStatus(sort.isAscending)
        }
    }

    suspend fun getTask(taskId: Long): Task = taskDao.getTask(taskId)

    suspend fun insertTask(task: Task): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(taskId: Long) = taskDao.deleteTask(taskId)
}