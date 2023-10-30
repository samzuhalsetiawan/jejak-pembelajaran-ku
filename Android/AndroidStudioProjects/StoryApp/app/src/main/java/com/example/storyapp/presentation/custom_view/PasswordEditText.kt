package com.example.storyapp.presentation.custom_view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.Button
import com.example.storyapp.R
import com.google.android.material.textfield.TextInputLayout

class PasswordEditText : TextInputLayout, TextWatcher {

    private var integratedButton: Button? = null
    private var errorText = resources.getString(R.string.error_text_password_etl)
    private var passwordValidatorEditText: PasswordEditText? = null
    private var integratedEditText: List<TextInputLayout>? = null


    private fun init() { editText?.addTextChangedListener(this) }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun afterTextChanged(s: Editable?) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        s ?: return
        if (passwordValidatorEditText == null) {
            if (s.length < 8) showError() else closeError()
        } else {
            val password = passwordValidatorEditText?.editText?.text ?: return
            if (password.toString() != s.toString()) showError() else closeError()
        }
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

    fun setupWithButton(button: Button, vararg textField: TextInputLayout) {
        integratedEditText = textField.toList()
        button.isEnabled = false
        integratedButton = button
    }

    fun addPasswordVerifiedWith(passwordEditText: PasswordEditText) {
        errorText = resources.getString(R.string.error_text_confirm_password_etl)
        passwordValidatorEditText = passwordEditText
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)
}