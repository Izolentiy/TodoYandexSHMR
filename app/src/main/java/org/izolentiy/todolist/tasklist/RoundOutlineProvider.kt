package org.izolentiy.todolist.tasklist

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class RoundOutlineProvider(
    private val roundMode: RoundMode,
    private val cornerRadius: Int
) : ViewOutlineProvider() {
    private val topOffset
        get() = when(roundMode) {
            RoundMode.BOTTOM, RoundMode.NONE -> cornerRadius
            else -> 0
        }

    private val bottomOffset
        get() = when(roundMode) {
            RoundMode.TOP, RoundMode.NONE -> cornerRadius
            else -> 0
        }

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0,
            0 - topOffset,
            view.width,
            view.height + bottomOffset,
            cornerRadius.toFloat()
        )
    }

    enum class RoundMode {
        BOTTOM, TOP, NONE, ALL
    }
}