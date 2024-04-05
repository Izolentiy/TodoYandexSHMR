package org.izolentiy.todolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE is_done == 0")
    fun getOnlyUncompletedTasks(): Flow<List<Task>>

    @Insert
    suspend fun addNewTask(task: Task)
}