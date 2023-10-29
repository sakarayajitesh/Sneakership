package com.ajitesh.sneakership.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = lightColors(
    primary = Color(0XFFFFFFFF),
    onPrimary = OnPrimaryColor,
    secondary = Secondary
)


@Composable
fun SneakershipTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content,
        colors = colors,
        typography = MyTypography
    )
}