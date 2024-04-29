package org.izolentiy.todolist.taskeditor

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class InputWatcher(
    private val editText: EditText,
    private val viewModel: TaskEditorViewModel
) : TextWatcher {
    private val TAG = InputWatcher::class.simpleName

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(text: Editable?) {
        editText.removeTextChangedListener(this)

        val input = text.toString()
        viewModel.updateText(input)

        editText.addTextChangedListener(this)
    }
}