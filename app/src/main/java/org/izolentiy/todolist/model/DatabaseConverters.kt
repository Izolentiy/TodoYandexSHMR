package org.izolentiy.todolist.model

import androidx.room.TypeConverter
import java.util.Date

class DatabaseConverters {
    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun ordinalToPriority(ordinal: Int): Task.Priority {
        return Task.Priority.entries[ordinal]
    }

    @TypeConverter
    fun priorityToOrdinal(priority: Task.Priority): Int {
        return priority.ordinal
    }
}