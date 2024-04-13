package org.izolentiy.todolist.model

import androidx.recyclerview.widget.DiffUtil
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

    fun toMutableTask() = MutableTask(
        id = id,
        text = text,
        priority = priority,
        deadline = deadline,
        isDone = isDone,
        created = created,
        changed = changed
    )
}

object TaskComparator : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(old: Task, new: Task) = old.id == new.id
    override fun areContentsTheSame(old: Task, new: Task) = old == new
}
