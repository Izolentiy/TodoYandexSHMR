package org.izolentiy.todolist

import android.content.Context
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModel

fun Float.dpToPixels(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun <T : ViewModel> Context.getViewModel(type: Class<T>): T {
    val factory = (applicationContext as App).appComponent.viewModelFactory
    return factory.create(type)
}