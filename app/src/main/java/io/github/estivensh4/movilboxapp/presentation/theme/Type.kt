package io.github.estivensh4.movilboxapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import io.github.estivensh4.movilboxapp.R

object AppFont {
    val GilRoy = FontFamily(
        Font(R.font.gilroy_regular),
        Font(R.font.gilroy_bold, FontWeight.Bold),
        Font(R.font.gilroy_black, FontWeight.Black),
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = AppFont.GilRoy),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = AppFont.GilRoy),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = AppFont.GilRoy),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = AppFont.GilRoy),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = AppFont.GilRoy),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = AppFont.GilRoy),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = AppFont.GilRoy),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = AppFont.GilRoy),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = AppFont.GilRoy),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = AppFont.GilRoy),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = AppFont.GilRoy),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = AppFont.GilRoy),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = AppFont.GilRoy),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = AppFont.GilRoy),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = AppFont.GilRoy)
)