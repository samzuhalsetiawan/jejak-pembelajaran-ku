package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.storyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogSimpleWarning : DialogFragment() {

    private val alertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_dialog_simple_warning)
            .setMessage(R.string.dialog_simple_warning_default_message)
            .create()
    }

    private var customMessage: String? = null

    fun setMessage(message: String) {
        customMessage = message
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        alertDialog.setMessage(customMessage)
        return alertDialog
    }

}