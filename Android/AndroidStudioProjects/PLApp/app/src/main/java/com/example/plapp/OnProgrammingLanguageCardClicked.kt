package com.example.plapp

import android.view.View

fun interface OnProgrammingLanguageCardClicked {
    fun onCardClicked(view: View, programmingLanguage: ProgrammingLanguage)
}