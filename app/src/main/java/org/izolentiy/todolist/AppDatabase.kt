package org.izolentiy.todolist

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.runBlocking
import org.izolentiy.todolist.model.DateConverters
import org.izolentiy.todolist.model.Task
import java.util.Date
import java.util.concurrent.Executors

@Database(entities = [Task::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}