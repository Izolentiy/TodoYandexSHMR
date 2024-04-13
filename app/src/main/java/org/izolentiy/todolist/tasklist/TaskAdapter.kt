package org.izolentiy.todolist.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.izolentiy.todolist.model.Task
import org.izolentiy.todolist.model.TaskComparator
import org.izolentiy.todolist.databinding.ItemTaskBinding as Binding

class TaskAdapter(
    private val onTaskClickListener: (task: Task) -> Unit
) : ListAdapter<Task, TaskAdapter.ViewHolder>(TaskComparator) {
    inner class ViewHolder(
        private val binding: Binding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onTaskClickListener(getItem(adapterPosition))
            }
        }

        fun bind(task: Task) {
            binding.apply {
                textviewContent.text = task.text
                imageStatus.isChecked = task.isDone
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}