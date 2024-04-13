package org.izolentiy.todolist.taskeditor

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

class InputWatcher(
    private val editText: EditText,
    private val viewModel: TaskEditorViewModel
) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(text: Editable?) {
        editText.removeTextChangedListener(this)
        viewModel.updateText(text.toString())
        editText.addTextChangedListener(this)
    }
}