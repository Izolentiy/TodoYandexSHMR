package org.izolentiy.todolist.tasklist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.izolentiy.todolist.App
import org.izolentiy.todolist.R
import org.izolentiy.todolist.databinding.FragmentTaskListBinding
import org.izolentiy.todolist.newtask.TaskEditorFragment

class TaskListFragment : Fragment() {
    private lateinit var viewModel: TaskListViewModel
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    private val TAG = TaskListFragment::class.simpleName

    override fun onAttach(context: Context) {
        val factory = (context.applicationContext as App).appComponent.viewModelFactory
        viewModel = factory.create(TaskListViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val taskAdapter = TaskAdapter()
        binding.recyclerviewTasks.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
        binding.fabNew.setOnClickListener {
            Log.d(TAG, "Add new task")
//            viewModel.addNewTask()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, TaskEditorFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.taskList.collect {
                    taskAdapter.submitList(it)
                }
            }
        }
        return binding.root
    }
}