package com.example.storyapp.presentation.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.storyapp.R
import com.example.storyapp.presentation.main.MainViewModel
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogIconProfile : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _dialogIconProfileListener: DialogIconProfileListener? = null

    fun interface DialogIconProfileListener {
        fun onLogoutClicked()
    }

    fun setOnLogoutClicked(listener: DialogIconProfileListener) {
        _dialogIconProfileListener = listener
    }

    private val alertDialog by lazy {
        val logoutLabel = resources.getString(R.string.label_btn_logout)
        val logoutSpanText = SpannableString(logoutLabel)
        logoutSpanText.setSpan(
            ForegroundColorSpan(MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorError, Color.RED)),
            0, logoutLabel.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_dialog_icon_profile)
            .setMessage("\nUsername: ${mainViewModel.username.value}")
            .setNeutralButton(logoutSpanText) { _,_ -> _dialogIconProfileListener?.onLogoutClicked() }
            .create()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return alertDialog
    }

}