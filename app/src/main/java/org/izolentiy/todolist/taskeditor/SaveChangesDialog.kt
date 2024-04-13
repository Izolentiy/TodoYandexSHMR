package org.izolentiy.todolist.taskeditor

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.izolentiy.todolist.R

class SaveChangesDialog : DialogFragment() {
    interface Listener {
        fun onPositiveSaveDialog()
        fun onNegativeSaveDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.discard_changes_dialog_title)
            .setPositiveButton(R.string.discard_changes_dialog_positive_action) { _, _ ->
                (requireParentFragment() as Listener).onPositiveSaveDialog()
            }
            .setNegativeButton(R.string.discard_changes_dialog_negative_action) { _, _ ->
                (requireParentFragment() as Listener).onNegativeSaveDialog()
            }
            .create()
    }

}