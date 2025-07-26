package com.moduro.barrier_free_app.presentation.map.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

fun createCircleClusterBitmap(context: Context, count: Int): Bitmap {
    val size = 40  // dp
    val density = context.resources.displayMetrics.density
    val px = (size * density).toInt()

    val bitmap = Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    // Draw circle
    canvas.drawCircle(px / 2f, px / 2f, px / 2f, paint)

    // Draw text
    paint.color = Color.WHITE
    paint.textSize = 14 * density
    paint.textAlign = Paint.Align.CENTER

    val textY = (px / 2 - (paint.descent() + paint.ascent()) / 2)
    canvas.drawText(count.toString(), px / 2f, textY, paint)

    return bitmap
}
