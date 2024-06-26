package org.izolentiy.todolist.tasklist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.launch
import org.izolentiy.todolist.R
import org.izolentiy.todolist.databinding.FragmentTaskListBinding
import org.izolentiy.todolist.dpToPixels
import org.izolentiy.todolist.getViewModel
import org.izolentiy.todolist.model.Task
import org.izolentiy.todolist.taskeditor.TaskEditorFragment
import org.izolentiy.todolist.taskeditor.TaskEditorFragment.Companion.TASK_ID

class TaskListFragment : Fragment() {
    private lateinit var viewModel: TaskListViewModel
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private val TAG = TaskListFragment::class.simpleName

    private val onTaskClickListener: (task: Task) -> Unit = {
        val args = bundleOf(TASK_ID to it.id)
        requireActivity().supportFragmentManager.commit {
            replace(R.id.main_fragment_container, TaskEditorFragment.newInstance(args))
            addToBackStack(null)
        }
    }

    private val onTaskCheckListener: (
        task: Task, checked: Boolean
    ) -> Unit = { task, checked ->
        viewModel.updateTask(task.copy(isDone = checked))
    }

    override fun onAttach(context: Context) {
        viewModel = context.getViewModel(this, TaskListViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val taskAdapter = TaskAdapter(onTaskCheckListener, onTaskClickListener)
        binding.recyclerviewTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MaterialDividerItemDecoration(
                context, RecyclerView.VERTICAL
            ).apply {
                isLastItemDecorated = false
                dividerColor = ResourcesCompat.getColor(
                    context.resources, R.color.supportSeparator, context.theme
                )
                dividerThickness = 0.5F.dpToPixels(context)
            })
        }
        binding.fabNew.setOnClickListener {
            Log.d(TAG, "Add new task")
            requireActivity().supportFragmentManager.commit {
                replace(R.id.main_fragment_container, TaskEditorFragment.newInstance())
                addToBackStack(null)
            }
        }

        binding.toolbarTaskList.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_sort -> showSortTaskDialog()
                R.id.action_filter -> showFilterTaskDialog()
                R.id.action_settings -> showSettings()
                else -> false
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d(TAG, "onCreateView: resumed")
                viewModel.taskList.collect {
                    Log.d(TAG, "onCreateView: item collected")
                    taskAdapter.submitList(it)
                }
            }
        }
        return binding.root
    }

    private fun showSettings(): Boolean {
        return true
    }

    private fun showFilterTaskDialog(): Boolean {
        val order = viewModel.currentSort.isAscending
        val sort = TaskSort(TaskSort.Type.BY_STATUS, !order)
        viewModel.applySort(sort)
        return true
    }

    private fun showSortTaskDialog(): Boolean {
        Log.d(TAG, "showFilterTaskDialog: START SORT DIALOG")
        SortTaskDialog().show(childFragmentManager, "sort_task_dialog")
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}