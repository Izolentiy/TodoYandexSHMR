package org.izolentiy.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "priority") val priority: Priority,
    @ColumnInfo(name = "deadline") val deadline: Date?,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "created") val created: Date,
    @ColumnInfo(name = "changed") val changed: Date,
) {
    enum class Priority {
        LOW, BASIC, HIGH
    }
}
