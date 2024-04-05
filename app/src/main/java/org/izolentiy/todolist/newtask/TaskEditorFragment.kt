package org.izolentiy.todolist.newtask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.izolentiy.todolist.App
import org.izolentiy.todolist.R
import org.izolentiy.todolist.databinding.FragmentTaskEditorBinding

class TaskEditorFragment : Fragment() {
    private lateinit var viewModel: TaskEditorViewModel
    private var _binding: FragmentTaskEditorBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        val factory = (context.applicationContext as App).appComponent.viewModelFactory
        viewModel = factory.create(TaskEditorViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = TaskEditorFragment()
    }

}