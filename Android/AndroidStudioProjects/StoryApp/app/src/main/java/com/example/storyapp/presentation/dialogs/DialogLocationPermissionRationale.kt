package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.storyapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogLocationPermissionRationale : DialogFragment() {

    interface DialogLocationPermissionRationaleListener {
        fun onPositiveButtonClicked()
    }

    var dialogLocationPermissionRationaleListener: DialogLocationPermissionRationaleListener? = null

    private val alertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.permission_rationale_title)
            .setMessage(R.string.location_permission_rationale_message)
            .setPositiveButton(R.string.label_btn_positive_got_it) { _, _ -> dialogLocationPermissionRationaleListener?.onPositiveButtonClicked() }
            .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return alertDialog
    }

}