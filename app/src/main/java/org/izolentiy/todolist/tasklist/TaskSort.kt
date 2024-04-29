package org.izolentiy.todolist.tasklist

import androidx.annotation.IdRes
import org.izolentiy.todolist.R

data class TaskSort(val type: Type, val isAscending: Boolean = true) {
    enum class Type(@IdRes val propertyId: Int) {
        BY_DATE_CREATED(R.id.radio_date_created),
        BY_DATE_MODIFIED(R.id.radio_date_modified),
        BY_IMPORTANCE(R.id.radio_importance),
        BY_DEADLINE(R.id.radio_deadline),
        BY_STATUS(R.id.radio_status)
    }
}