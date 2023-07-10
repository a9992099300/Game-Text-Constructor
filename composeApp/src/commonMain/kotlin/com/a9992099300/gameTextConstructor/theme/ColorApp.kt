package com.a9992099300.gameTextConstructor.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ColorsApp(
    val primaryBackground: Color,
    val primaryAction: Color,
    val primaryTextColor: Color,
    val hintTextColor: Color,
    val highlightTextColor: Color,
    val secondaryTextColor: Color,
    val thirdTextColor: Color,
    val tagColor: Color,
    val tagTextColor: Color,
    val textFieldBackground: Color
)

val palette = ColorsApp(
    primaryBackground = Color(0xFF26252d),
    primaryAction = Color(0xFFFd77d31),
    primaryTextColor = Color(0xFF050B18),
    hintTextColor = Color(0xFF696C75),
    highlightTextColor = Color(0xFFF4D144),
    secondaryTextColor = Color(0xFFFFFFFF),
    thirdTextColor = Color(0xFFEEF2FB),
    tagColor = Color(0x1844A9F4),
    tagTextColor = Color(0xFF44A9F4),
    textFieldBackground = Color(     0xFFA6A5B1)
)

val LocalColorProvider = staticCompositionLocalOf<ColorsApp> { error("No default implementation") }