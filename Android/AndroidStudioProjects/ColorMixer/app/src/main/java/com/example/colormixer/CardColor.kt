package com.example.colormixer

import android.graphics.Color

object CardColor {
    var warna1 = Color.rgb(1f, 1f, 1f)
    var warna2 = Color.rgb(1f, 1f, 1f)
    var warna3 = Color.rgb(1f, 1f, 1f)
    val templateWarna: List<Int> = listOf(
        Color.BLUE,
        Color.CYAN,
        Color.DKGRAY,
        Color.GRAY,
        Color.GREEN,
        Color.LTGRAY,
        Color.MAGENTA,
        Color.RED,
        Color.YELLOW,
        Color.parseColor("#CA965C"),
        Color.parseColor("#FF8E00"),
        Color.WHITE
    )
}