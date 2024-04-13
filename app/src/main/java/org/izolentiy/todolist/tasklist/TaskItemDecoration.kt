package org.izolentiy.todolist.tasklist

import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import org.izolentiy.todolist.dpToPixels

class TaskItemDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {
    private val TAG = TaskItemDecoration::class.simpleName
    private val offset = 16F.dpToPixels(context)
    private val radius = 8F.dpToPixels(context)
}