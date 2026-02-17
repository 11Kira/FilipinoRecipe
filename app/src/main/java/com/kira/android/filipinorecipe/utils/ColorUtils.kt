package com.kira.android.filipinorecipe.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.kira.android.filipinorecipe.model.enums.Protein

class ColorUtils {
    val beefGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF8E241E), Color(0xFFD86A4F)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val porkGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF7B241C), Color(0xFFE6B0AA)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val chickenGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF7B5E10), Color(0xFFB9970B)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val seafoodGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1A5276), Color(0xFF5DADE2)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val vegetablesGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF186A3B), Color(0xFF7DCEA0)),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    fun getColorGradientBrush(protein: String): Brush {
        when (protein) {
            Protein.BEEF.toString() -> {
                return beefGradient
            }

            Protein.PORK.toString() -> {
                return porkGradient
            }

            Protein.CHICKEN.toString() -> {
                return chickenGradient
            }

            Protein.SEAFOOD.toString() -> {
                return seafoodGradient
            }

            Protein.VEGETABLES.toString() -> {
                return vegetablesGradient
            }

            else -> {
                return beefGradient
            }
        }
    }
}