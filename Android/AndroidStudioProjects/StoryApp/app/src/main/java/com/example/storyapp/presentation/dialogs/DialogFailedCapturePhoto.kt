package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.storyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogFailedCapturePhoto : DialogFragment() {

    private val alertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_dialog_failed_capture_photo)
            .setMessage(R.string.dialog_failed_capture_photo_message)
            .setPositiveButton(R.string.label_btn_positive_ok) { _, _ -> dismiss() }
            .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return alertDialog
    }

}