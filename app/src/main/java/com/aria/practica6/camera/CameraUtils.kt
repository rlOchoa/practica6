package com.aria.practica6.camera

import androidx.compose.ui.graphics.ColorMatrix

/**
 * Devuelve un ColorMatrix que simula ajustes ISO/EV según lux ambiental.
 */
fun simulateCameraColorMatrix(lux: Float): ColorMatrix {
    return when {
        lux < 10f -> {
            // Baja luz: tono frío y oscuro
            ColorMatrix(
                floatArrayOf(
                    0.5f, 0f, 0f, 0f, 0f,
                    0f, 0.5f, 0f, 0f, 0f,
                    0f, 0f, 0.6f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }

        lux < 200f -> {
            // Luz normal
            ColorMatrix() // identidad
        }

        else -> {
            // Mucha luz: simular ISO bajo / tonos cálidos
            ColorMatrix(
                floatArrayOf(
                    1.2f, 0f, 0f, 0f, 20f,
                    0f, 1.1f, 0f, 0f, 10f,
                    0f, 0f, 0.9f, 0f, 0f,
                    0f, 0f, 0f, 1f, 0f
                )
            )
        }
    }
}