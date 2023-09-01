package com.example.mysmarthouse.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Green400 = Color(0xFF66BB6A)
val Green200 = Color(0xFFA5D6A7)
val Brown200 = Color(0xFFBCAAA4)
val Brown400 = Color(0xFF8D6E63)
val Teal200 = Color(0xFF03DAC5)
val Red400 = Color(0xFFCF6679)

internal val wearColorPalette: Colors = Colors(
    primary = Green200,
    primaryVariant = Green400,
    secondary = Brown200,
    secondaryVariant = Brown400,
    error = Red400,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onError = Color.Black,
)