package com.aria.practica6.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorMatrixColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.aria.practica6.camera.simulateCameraColorMatrix

@Composable
fun CameraOverlay(
    lightValue: Float
) {
    val matrix = simulateCameraColorMatrix(lightValue)

    val paint = Paint().apply {
        colorFilter = ColorMatrixColorFilter(matrix)
        alpha = 0.3f // Transparencia parcial (0.0f a 1.0f)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.drawRect(0f, 0f, size.width, size.height, paint)
            canvas.restore()
        }
    }
}