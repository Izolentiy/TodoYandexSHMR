package org.izolentiy.todolist.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.izolentiy.todolist.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task WHERE is_done = 0")
    fun getOnlyUncompletedTasks(): Flow<List<Task>>

    @Query(
        "SELECT * FROM Task ORDER BY " +
        "CASE WHEN :ascending = 1 THEN changed END ASC," +
        "CASE WHEN :ascending = 0 THEN changed END DESC"
    )
    fun getSortedByDateModified(ascending: Boolean): Flow<List<Task>>

    @Query(
        "SELECT * FROM Task ORDER BY " +
        "CASE WHEN :ascending = 1 THEN created END ASC," +
        "CASE WHEN :ascending = 0 THEN created END DESC"
    )
    fun getSortedByDateCreated(ascending: Boolean): Flow<List<Task>>

    @Query(
        "SELECT * FROM Task ORDER BY " +
        "CASE WHEN :ascending = 1 THEN deadline END ASC," +
        "CASE WHEN :ascending = 0 THEN deadline END DESC"
    )
    fun getSortedByDeadline(ascending: Boolean): Flow<List<Task>>

    @Query(
        "SELECT * FROM Task ORDER BY " +
        "CASE WHEN :ascending = 1 THEN priority END ASC," +
        "CASE WHEN :ascending = 0 THEN priority END DESC"
    )
    fun getSortedByImportance(ascending: Boolean): Flow<List<Task>>

    @Query(
        "SELECT * FROM Task ORDER BY " +
        "CASE WHEN :ascending = 1 THEN is_done END ASC," +
        "CASE WHEN :ascending = 0 THEN is_done END DESC"
    )
    fun getSortedByStatus(ascending: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :taskId")
    suspend fun getTask(taskId: Long): Task

    @Query("DELETE FROM Task WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Insert(Task::class, OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Update(Task::class)
    suspend fun updateTask(task: Task)

}