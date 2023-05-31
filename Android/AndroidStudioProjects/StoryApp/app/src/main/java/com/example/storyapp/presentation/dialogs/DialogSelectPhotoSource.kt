package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.storyapp.R
import com.example.storyapp.databinding.DialogPhotoSourceBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogSelectPhotoSource : DialogFragment() {

    interface DialogSelectPhotoSourceListener {
        fun onCameraOptionSelected()
        fun onGalleryOptionSelected()
    }

    private val binding by lazy { DialogPhotoSourceBinding.inflate(layoutInflater) }

    private val alertDialog by lazy {
        binding.cvCamera.setOnClickListener {
            optionSelectListener?.onCameraOptionSelected()
            dismiss()
        }
        binding.cvGallery.setOnClickListener {
            optionSelectListener?.onGalleryOptionSelected()
            dismiss()
        }
        MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setNegativeButton(R.string.label_btn_negative) { d, _ -> d.dismiss() }
            .create()
    }

    var optionSelectListener: DialogSelectPhotoSourceListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return alertDialog
    }

}