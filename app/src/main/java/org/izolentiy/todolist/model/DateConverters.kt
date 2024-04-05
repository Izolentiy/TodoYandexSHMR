package org.izolentiy.todolist.model

import androidx.room.TypeConverter
import java.util.Date

class DateConverters {
    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}