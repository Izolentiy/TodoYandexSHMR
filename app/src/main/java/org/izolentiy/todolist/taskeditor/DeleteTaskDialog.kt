package org.izolentiy.todolist.taskeditor

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.izolentiy.todolist.R

class DeleteTaskDialog : DialogFragment() {
    interface Listener {
        fun onPositiveDeleteDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
            .setTitle(R.string.delete_task_dialog_title)
            .setPositiveButton(R.string.delete_task_dialog_positive_action) { _, _ ->
                (requireParentFragment() as Listener).onPositiveDeleteDialog()
            }
            .setNegativeButton("Нет") { _, _ ->
                dialog?.dismiss()
            }
            .create()
    }
}