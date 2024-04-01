package org.izolentiy.todolist.model

import java.util.Date

data class Task(
    val id: String,
    val text: String,
    val priority: Priority,
    val isDone: Boolean,
    val created: Date
) {
    enum class Priority {
        LOW, REGULAR, HIGH
    }
}
