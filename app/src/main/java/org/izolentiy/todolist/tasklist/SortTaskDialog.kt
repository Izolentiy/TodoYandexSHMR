package org.izolentiy.todolist.tasklist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.izolentiy.todolist.R
import org.izolentiy.todolist.getViewModel
import org.izolentiy.todolist.databinding.DialogSortTaskBinding as Binding

class SortTaskDialog : BottomSheetDialogFragment() {
    private val TAG = SortTaskDialog::class.simpleName
    private lateinit var viewModel: TaskListViewModel
    private var _binding: Binding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        viewModel = context.getViewModel(parentFragment, TaskListViewModel::class.java)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Binding.inflate(inflater, container, false)

        val orderId = when(viewModel.currentSort.isAscending) {
            true -> R.id.radio_ascending
            else -> R.id.radio_descending
        }
        binding.radioGroupOrder.check(orderId)
        val propertyId = viewModel.currentSort.type.propertyId
        binding.radioGroupProperty.check(propertyId)

        binding.configureApplySortButton()
        binding.buttonCancelSort.setOnClickListener {
            dialog?.dismiss()
        }

        (dialog as? BottomSheetDialog)?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        return binding.root
    }

    private fun Binding.configureApplySortButton() {
        buttonApplySort.setOnClickListener {
            val type = TaskSort.Type.entries.find {
                it.propertyId == radioGroupProperty.checkedRadioButtonId
            }
            val isAscending = radioGroupOrder.checkedRadioButtonId == R.id.radio_ascending
            val taskSort = TaskSort(requireNotNull(type), isAscending)
            viewModel.applySort(taskSort)
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}