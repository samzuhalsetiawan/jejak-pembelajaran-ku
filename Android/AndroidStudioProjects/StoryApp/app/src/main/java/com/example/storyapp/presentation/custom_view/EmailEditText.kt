package com.example.storyapp.presentation.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Button
import com.example.storyapp.R
import com.google.android.material.textfield.TextInputLayout

class EmailEditText : TextInputLayout, TextWatcher {

    private var integratedButton: Button? = null
    private var errorText = resources.getString(R.string.error_text_email_etl)
    private var integratedEditText: List<TextInputLayout>? = null

    private val validEmailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (validEmailRegex matches s.toString()) closeError() else showError()
    }

    private fun closeError() {
        integratedEditText?.let { etl ->
            if (etl.none { it.isErrorEnabled }) {
                integratedButton?.isEnabled = etl.none { it.editText!!.text.isBlank() }
                isErrorEnabled = false
                error = null
            } else return
        } ?: run {
            integratedButton?.isEnabled = true
            isErrorEnabled = false
            error = null
        }
    }

    private fun showError() {
        integratedButton?.isEnabled = false
        error = errorText
        isErrorEnabled = true
    }

    fun setupWithButton(button: Button) {
        button.isEnabled = false
        integratedButton = button
    }

    fun setupWithButton(button: Button, vararg textField: TextInputLayout) {
        integratedEditText = textField.toList()
        button.isEnabled = false
        integratedButton = button
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        editText?.addTextChangedListener(this)
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

}