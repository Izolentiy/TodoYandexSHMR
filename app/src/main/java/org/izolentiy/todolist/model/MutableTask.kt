package org.izolentiy.todolist.model

import java.util.Date

class MutableTask(
    var id: Long = 0L,
    var text: String = "",
    var priority: Task.Priority = Task.Priority.BASIC,
    var deadline: Date? = null,
    var isDone: Boolean = false,
    var created: Date = Date(0),
    var changed: Date = Date(0),
) {
    fun toTask(): Task = Task(
        id = id,
        text = text,
        priority = priority,
        deadline = deadline,
        isDone = isDone,
        created = created,
        changed = changed
    )
}