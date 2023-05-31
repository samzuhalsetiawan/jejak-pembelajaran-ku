package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.storyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogCameraPermissionNeeded : DialogFragment() {

    interface DialogCameraPermissionNeededListener {
        fun onPositiveButtonClicked()
    }

    var dialogCameraPermissionNeededListener: DialogCameraPermissionNeededListener? = null

    private val alertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.permission_denied_dialog_title)
            .setMessage(R.string.camera_permission_denied_dialog_message)
            .setPositiveButton(R.string.label_btn_positive_got_it) { _, _ -> dialogCameraPermissionNeededListener?.onPositiveButtonClicked() }
            .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return alertDialog
    }

}