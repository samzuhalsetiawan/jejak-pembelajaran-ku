package com.example.storyapp.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.storyapp.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import kotlin.math.roundToInt

class PhotoPreview : ShapeableImageView {

    private val cornerRadius = 40
    private val iconSize = 100
    private val fontSize = 40f
    private val gapIconAndLabel = 8
    private val label = resources.getString(R.string.label_photo_picker)
    private val labelHeight by lazy { paintLabelText.descent() - paintLabelText.ascent() }
    private val iconAndLabelHeight by lazy { iconSize + gapIconAndLabel + labelHeight }
    private val iconPosX by lazy { (width / 2f) - (iconSize / 2f) }
    private val iconPosY by lazy { (height / 2f) - (iconAndLabelHeight / 2f) }
    private val primaryContainer by lazy { MaterialColors.getColor(context, com.google.android.material.R.attr.colorPrimaryContainer, Color.GRAY) }
    private val onPrimaryContainer by lazy { MaterialColors.getColor(context, com.google.android.material.R.attr.colorOnPrimaryContainer, Color.WHITE) }
    private val outlineColor by lazy { MaterialColors.getColor(context, com.google.android.material.R.attr.colorOutline, Color.GRAY) }

    private val icon by lazy {
        ContextCompat.getDrawable(context, R.drawable.ic_image_search)?.apply {
            setBounds(iconPosX.roundToInt(), iconPosY.roundToInt(), iconPosX.roundToInt() + iconSize, iconPosY.roundToInt() + iconSize)
            setTint(onPrimaryContainer)
        }
    }

    private val appearanceModel by lazy {
        shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, cornerRadius.toFloat())
            .build()
    }

    private val paintDashBorder by lazy {
        Paint().apply {
            setARGB(255,0,0,0)
            style = Paint.Style.STROKE
            color = outlineColor
            strokeWidth = 8f
            pathEffect = DashPathEffect(floatArrayOf(30f, 18f), 0f)
        }
    }

    private val pathDashBorder by lazy {
        Path().apply {
            addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(), Path.Direction.CCW)
        }
    }

    private val labelPosX by lazy { width / 2f }
    private val labelPosY by lazy { (height / 2f) + (iconAndLabelHeight / 2) }
    private val paintLabelText by lazy {
        Paint().apply {
            color = onPrimaryContainer
            textSize = fontSize
            textAlign = Paint.Align.CENTER
        }
    }
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()

        shapeAppearanceModel = appearanceModel

        setBackgroundColor(primaryContainer)

        if (drawable == null) {
            canvas?.let { icon?.draw(it) }
            canvas?.drawPath(pathDashBorder, paintDashBorder)
            canvas?.drawText(label, labelPosX, labelPosY, paintLabelText )
        }

        canvas?.restore()
    }
}