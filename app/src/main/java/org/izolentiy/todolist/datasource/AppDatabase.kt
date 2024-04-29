package org.izolentiy.todolist.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.izolentiy.todolist.model.DatabaseConverters
import org.izolentiy.todolist.model.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}