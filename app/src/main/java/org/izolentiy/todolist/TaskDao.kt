package org.izolentiy.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE is_done = 0")
    fun getOnlyUncompletedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :taskId")
    suspend fun getTask(taskId: Long): Task

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Insert(Task::class, OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

}