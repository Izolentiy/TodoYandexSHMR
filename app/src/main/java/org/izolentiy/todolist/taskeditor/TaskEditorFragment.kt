package org.izolentiy.todolist.taskeditor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import org.izolentiy.todolist.R
import org.izolentiy.todolist.getViewModel
import org.izolentiy.todolist.model.MutableTask
import org.izolentiy.todolist.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import org.izolentiy.todolist.databinding.FragmentTaskEditorBinding as Binding

class TaskEditorFragment : Fragment(), DeleteTaskDialog.Listener, SaveChangesDialog.Listener {
    private val TAG = TaskEditorFragment::class.simpleName
    private lateinit var viewModel: TaskEditorViewModel
    private var _binding: Binding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        viewModel = context.getViewModel(TaskEditorViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Binding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val id = arguments?.getLong(TASK_ID)
            viewModel.loadTask(id).join()
            binding.apply {
                updateTitleEdittext(viewModel.task)
                updatePriorityToggleGroup(viewModel.task)
                configureTitleEdittext()
                configurePriorityToggleGroup()
                configureDeadlineSwitch()
                configureToolbar()
            }
            Log.d(TAG, "onCreateView: LOADING_WAITED")
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.taskState.collect { task ->
                    binding.apply {
                        updateDeadlineSwitch(task)
                        updateTextDeadlineDate(task)
                    }
                }
            }
            Log.d(TAG, "onCreateView: CONFIGURATION_COMPLETED")
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun Binding.updateTitleEdittext(task: Task) {
        edittextTitle.setText(task.text)
    }

    private fun Binding.updatePriorityToggleGroup(task: Task) {
        val priorityButtonId = when (task.priority) {
            Task.Priority.BASIC -> R.id.button_reg_priority
            Task.Priority.LOW -> R.id.button_low_priority
            Task.Priority.HIGH -> R.id.button_high_priority
        }
        toggleGroupPriorities.check(priorityButtonId)
    }

    private fun Binding.updateTextDeadlineDate(task: Task) {
        val date = if (task.deadline != null) {
            val locale = resources.configuration.locales[0]
            val formatter = SimpleDateFormat("d MMMM yyyy", locale)
            formatter.format(task.deadline)
        } else {
            "---"
        }
        Log.d(TAG, "configureDeadlineSwitch: DEADLINE_CONFIGURATION_ENDED")
        textviewDeadlineDate.text = date
    }

    private fun Binding.updateDeadlineSwitch(task: Task) {
        switchDeadlineOn.isChecked = task.deadline != null
    }

    private fun Binding.configureTitleEdittext() {
        edittextTitle.addTextChangedListener(InputWatcher(edittextTitle, viewModel))
    }

    private fun Binding.configurePriorityToggleGroup() {
        toggleGroupPriorities.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            val priority = when (checkedId) {
                R.id.button_reg_priority -> Task.Priority.BASIC
                R.id.button_low_priority -> Task.Priority.LOW
                R.id.button_high_priority -> Task.Priority.HIGH
                else -> Task.Priority.BASIC
            }
            viewModel.updatePriority(priority)
        }
    }

    private fun Binding.configureDeadlineSwitch() {
        Log.d(TAG, "configureDeadlineSwitch: DEADLINE_CONFIGURATION_STARTED")
        switchDeadlineOn.setOnCheckedChangeListener { _, state ->
            when (state) {
                false -> {
                    Log.d(TAG, "configureDeadlineSwitch: SWITCH_UNCHECKED")
                    viewModel.updateDeadline(null)
                }
                true -> {
                    Log.d(TAG, "configureDeadlineSwitch: SWITCH_CHECKED")
                    if (viewModel.task.deadline == null) {
                        showDatePicker()
                    }
                }
            }
        }
    }

    private fun Binding.configureToolbar() {
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_save -> saveTask()
                R.id.action_delete -> deleteTask()
            }
            true
        }
        toolbar.setNavigationOnClickListener {
            if (viewModel.savedTask == viewModel.task) {
                parentFragmentManager.popBackStack()
            } else {
                SaveChangesDialog().show(childFragmentManager, "save_changes_dialog")
            }
        }
    }

    private fun deleteTask() {
        DeleteTaskDialog().show(childFragmentManager, "task_delete_dialog")
    }

    private fun saveTask() {
        viewModel.saveTask()
    }

    private fun showDatePicker() {
        Log.d(TAG, "showDatePicker: DATE_PICKER CALLED")
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraints)
            .build()

        datePicker.apply {
            addOnPositiveButtonClickListener { time ->
                viewModel.updateDeadline(Date(time))
                val locale = resources.configuration.locales[0]
                val formatter = SimpleDateFormat("d MMMM yyyy", locale)
                binding.textviewDeadlineDate.text = formatter.format(viewModel.task.deadline!!)
            }
            addOnDismissListener {
                if (viewModel.task.deadline != null) return@addOnDismissListener
                binding.switchDeadlineOn.isChecked = false
            }
        }.show(parentFragmentManager, "date_picker")
    }

    override fun onPositiveDeleteDialog() {
        viewModel.deleteTask()
        parentFragmentManager.popBackStack()
    }

    override fun onPositiveSaveDialog() {
        viewModel.saveTask()
        parentFragmentManager.popBackStack()
    }

    override fun onNegativeSaveDialog() {
        parentFragmentManager.popBackStack()
    }

    companion object {
        const val TASK_ID = "task_id"
        fun newInstance(args: Bundle? = null) = TaskEditorFragment()
            .apply { arguments = args }
    }
}