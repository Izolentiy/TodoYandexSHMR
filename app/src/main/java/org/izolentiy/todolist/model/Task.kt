package org.izolentiy.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "priority") val priority: Priority = Priority.BASIC,
    @ColumnInfo(name = "deadline") val deadline: Date? = null,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
    @ColumnInfo(name = "created") val created: Date = Date(0),
    @ColumnInfo(name = "changed") val changed: Date = Date(0),
) {
    enum class Priority {
        LOW, BASIC, HIGH
    }
}
