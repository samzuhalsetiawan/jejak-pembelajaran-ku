package com.example.storyapp.presentation.custom_view

import android.text.TextPaint
import android.text.style.CharacterStyle

class ShadowSpan(
    private val radius: Float,
    private val dx: Float,
    private val dy: Float,
    private val shadowColor: Int,
    private val textColor: Int
) : CharacterStyle() {
    override fun updateDrawState(tp: TextPaint?) {
        tp?.color = textColor
        tp?.setShadowLayer(radius,dx,dy,shadowColor)
    }
}