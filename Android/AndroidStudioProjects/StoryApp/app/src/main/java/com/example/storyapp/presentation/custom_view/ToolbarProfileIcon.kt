package com.example.storyapp.presentation.custom_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.ActionProvider
import com.example.storyapp.R

class ToolbarProfileIcon(private val context: Context) : ActionProvider(context) {

    fun interface OnProfileIconClicked {
        fun onProfileIconClicked()
    }

    private var _username = "Unknown"
    private var _tvUserInitial: TextView? = null
    private var _onProfileIconClicked: OnProfileIconClicked? = null

    fun setOnProfileIconClicked(listener: OnProfileIconClicked) {
        _onProfileIconClicked = listener
    }

    fun setUsername(name: String) {
        _tvUserInitial?.let {
            it.text = name.first().toString()
        } ?: run { _username = name.first().toString() }
    }

    private val profileIconView by lazy {
        LayoutInflater.from(context).inflate(R.layout.custom_menu_profile_icon, null).also {
            val tvIcon = it.findViewById<TextView>(R.id.tvUserInitial)
            _tvUserInitial = tvIcon
            tvIcon.text = _username
            tvIcon.setOnClickListener { _onProfileIconClicked?.onProfileIconClicked() }
        }
    }

    override fun onCreateActionView(): View {
        return profileIconView
    }
}