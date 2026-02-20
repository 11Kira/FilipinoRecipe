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
        colors = listOf(Color(0xFF702632), Color(0xFF964F4F)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val chickenGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF7B5E10), Color(0xFFB9970B)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val seafoodGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF154360), Color(0xFF21618C)),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val vegetablesGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF1B4F31), Color(0xFF3D8B5E)),
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

    fun getSubHeaderColor(protein: String): Color {
        when (protein) {
            Protein.BEEF.toString() -> {
                return Color(0xFFFFCCBC)
            }

            Protein.PORK.toString() -> {
                return Color(0xFFFADBD8)
            }

            Protein.CHICKEN.toString() -> {
                return Color(0xFFFEF9E7)
            }

            Protein.SEAFOOD.toString() -> {
                return Color(0xFFAED6F1)
            }

            Protein.VEGETABLES.toString() -> {
                return Color(0xFFD5F5E3)
            }

            else -> {
                return Color(0xFFFFCCBC)
            }
        }
    }

    fun getDividerColor(protein: String): Color {
        when (protein) {
            Protein.BEEF.toString() -> {
                return Color.White.copy(alpha = 0.2f)
            }

            Protein.PORK.toString() -> {
                return Color.White.copy(alpha = 0.2f)
            }

            Protein.CHICKEN.toString() -> {
                return Color.White.copy(alpha = 0.15f)
            }

            Protein.SEAFOOD.toString() -> {
                return Color.White.copy(alpha = 0.2f)
            }

            Protein.VEGETABLES.toString() -> {
                return Color.White.copy(alpha = 0.2f)
            }

            else -> {
                return Color.White.copy(alpha = 0.2f)
            }
        }
    }

    val PastelBlue = Color(0xFFD6EEFF)
    val PastelPurple = Color(0xFFE2D7F7)
    val PastelMint = Color(0xFFE2F1E7)

    val recipeListBackgroundGradient = Brush.verticalGradient(
        colors = listOf(PastelBlue, PastelPurple, PastelMint)
    )
}