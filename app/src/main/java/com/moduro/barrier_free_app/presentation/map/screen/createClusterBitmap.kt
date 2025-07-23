package com.moduro.barrier_free_app.presentation.map.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface

fun createClusterBitmap(context: Context, count: Int, gu: String): Bitmap {
    val density = context.resources.displayMetrics.density
    val widthPx = (86 * density).toInt()
    val heightPx = (34 * density).toInt()
    val cornerRadiusPx = 17 * density

    val bitmap = Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)

    val paint = android.graphics.Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
    }

    val rect = RectF(0f, 0f, widthPx.toFloat(), heightPx.toFloat())
    canvas.drawRoundRect(rect, cornerRadiusPx, cornerRadiusPx, paint)

    val textSize = 14 * density
    val textPaint = android.graphics.Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = textSize
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    val countText = count.toString()
    val guText = gu

    val countColor = Color.parseColor("#FFF86C")
    val guColor = Color.WHITE

    val textMargin = 8 * density

    val totalTextWidth =
        textPaint.measureText(countText) + textPaint.measureText(guText) + textMargin

    val startX = (widthPx - totalTextWidth) / 2

    // 개수 텍스트
    textPaint.color = countColor
    canvas.drawText(countText, startX, heightPx / 2 + textSize / 3, textPaint)

    // 구 텍스트
    textPaint.color = guColor
    val countTextWidth = textPaint.measureText(countText)
    canvas.drawText(
        guText,
        startX + countTextWidth + textMargin,
        heightPx / 2 + textSize / 3,
        textPaint
    )

    return bitmap
}
