package org.izolentiy.todolist.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.izolentiy.todolist.model.Task
import org.izolentiy.todolist.databinding.ItemTaskBinding as Binding

class TaskAdapter(
    private val onTaskCheckListener: (task: Task, checked: Boolean) -> Unit,
    private val onTaskClickListener: (task: Task) -> Unit
) : ListAdapter<Task, TaskAdapter.ViewHolder>(TaskComparator) {
    inner class ViewHolder(
        private val binding: Binding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onTaskClickListener(getItem(adapterPosition))
            }
            binding.imageStatus.setOnCheckedChangeListener { _, checked ->
                onTaskCheckListener(getItem(adapterPosition), checked)
            }
        }

        fun bind(task: Task) {
            binding.textviewContent.text = task.text
            updateStatus(task.isDone)
        }

        fun updateStatus(status: Boolean) {
            binding.imageStatus.isChecked = status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        Binding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else payloads.forEach {
            it as Bundle
            if (it.containsKey(IS_DONE)) {
                val status = it.getBoolean(IS_DONE)
                holder.updateStatus(status)
            }
        }
    }

    object TaskComparator : DiffUtil.ItemCallback<Task>() {
        override fun getChangePayload(oldItem: Task, newItem: Task) = Bundle().apply {
            if (oldItem.isDone != newItem.isDone) {
                putBoolean(IS_DONE, newItem.isDone)
            }
        }

        override fun areItemsTheSame(old: Task, new: Task) = old.id == new.id
        override fun areContentsTheSame(old: Task, new: Task) = old == new
    }

    companion object {
        const val IS_DONE = "is_done"
    }
}