package com.moduro.barrier_free_app.core_ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.moduro.barrier_free_app.R

val barrierFreeFontBold = FontFamily(Font(R.font.suit_bold))
val barrierFreeFontSemiBold = FontFamily(Font(R.font.suit_semibold))
val barrierFreeFontMedium = FontFamily(Font(R.font.suit_medium))
//val barrierFreeFontExtraBold = FontFamily(Font(R.font.suit_bold))

@Immutable
data class barrierFreeTypography(
    val H1_SB: TextStyle,
    val H2_B: TextStyle,
    val H3_B: TextStyle,
    val H4_SB: TextStyle,
    val H5_SB_5: TextStyle,
    val H5_SB_10: TextStyle,
    val H5_M_10: TextStyle,
    val H5_B_5: TextStyle,
    val H6_M: TextStyle,
    val H7_M_5: TextStyle,
    val H7_M_10: TextStyle,
    val H8_SB: TextStyle,
    val H9_M: TextStyle,
    val H9_B: TextStyle,
    val H10_M: TextStyle
)

val defaultbarrierFreeTypography = barrierFreeTypography(
    H1_SB = TextStyle(
        fontFamily = barrierFreeFontSemiBold,
        fontSize = 25.sp,
        lineHeight = 33.sp,
        letterSpacing = (-0.05).em
    ),
    H2_B = TextStyle(
        fontFamily = barrierFreeFontBold,
        fontSize = 23.sp,
        lineHeight = 23.sp,
        letterSpacing = (-0.05).em
    ),
    H3_B = TextStyle(
        fontFamily = barrierFreeFontBold,
        fontSize = 20.sp,
        lineHeight = 33.sp,
        letterSpacing = (-0.05).em
    ),
    H4_SB = TextStyle(
        fontFamily = barrierFreeFontSemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.10).em
    ),
    H5_SB_5 = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = (-0.05).em
    ),
    H5_SB_10 = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        letterSpacing = (-0.10).em
    ),
    H5_B_5 = TextStyle(
        fontFamily = barrierFreeFontBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.05).em
    ),
    H6_M = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = (-0.10).em
    ),
    H7_M_5 = TextStyle(
        fontFamily = barrierFreeFontSemiBold,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = (-0.05).em
    ),
    H7_M_10 = TextStyle(
        fontFamily = barrierFreeFontSemiBold,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = (-0.10).em
    ),
    H5_M_10 = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = (-0.10).em
    ),
    H8_SB = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        letterSpacing = (-0.05).em
    ),
    H9_M = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 13.sp,
        lineHeight = 13.sp,
        letterSpacing = (-0.05).em
    ),
    H9_B = TextStyle(
        fontFamily = barrierFreeFontBold,
        fontSize = 13.sp,
        lineHeight = 13.sp,
        letterSpacing = (-0.05).em
    ),
    H10_M = TextStyle(
        fontFamily = barrierFreeFontMedium,
        fontSize = 12.sp,
        lineHeight = 12.sp,
        letterSpacing = (-0.10).em
    )
)
val LocalbarrierFreeTypographyProvider = staticCompositionLocalOf { defaultbarrierFreeTypography }

@Composable
fun ProvideScaledTypography(
    isLargeTextMode: Boolean,
    content: @Composable () -> Unit
) {
    val scaleMultiplier = if (isLargeTextMode) 1.3f else 1f

    val scaledTypography = barrierFreeTypography(
        H1_SB = defaultbarrierFreeTypography.H1_SB.copy(fontSize = (25 * scaleMultiplier).sp),
        H2_B = defaultbarrierFreeTypography.H2_B.copy(fontSize = (23 * scaleMultiplier).sp),
        H3_B = defaultbarrierFreeTypography.H3_B.copy(fontSize = (20 * scaleMultiplier).sp),
        H4_SB = defaultbarrierFreeTypography.H4_SB.copy(fontSize = (20 * scaleMultiplier).sp),
        H5_SB_5 = defaultbarrierFreeTypography.H5_SB_5.copy(fontSize = (18 * scaleMultiplier).sp),
        H5_SB_10 = defaultbarrierFreeTypography.H5_SB_10.copy(fontSize = (18 * scaleMultiplier).sp),
        H5_B_5 = defaultbarrierFreeTypography.H5_B_5.copy(fontSize = (18 * scaleMultiplier).sp),
        H6_M = defaultbarrierFreeTypography.H6_M.copy(fontSize = (16 * scaleMultiplier).sp),
        H7_M_5 = defaultbarrierFreeTypography.H7_M_5.copy(fontSize = (15 * scaleMultiplier).sp),
        H7_M_10 = defaultbarrierFreeTypography.H7_M_10.copy(fontSize = (15 * scaleMultiplier).sp),
        H5_M_10 = defaultbarrierFreeTypography.H5_M_10.copy(fontSize = (15 * scaleMultiplier).sp),
        H8_SB = defaultbarrierFreeTypography.H8_SB.copy(fontSize = (14 * scaleMultiplier).sp),
        H9_M = defaultbarrierFreeTypography.H9_M.copy(fontSize = (13 * scaleMultiplier).sp),
        H9_B = defaultbarrierFreeTypography.H9_B.copy(fontSize = (13 * scaleMultiplier).sp),
        H10_M = defaultbarrierFreeTypography.H10_M.copy(fontSize = (12 * scaleMultiplier).sp)
    )

    CompositionLocalProvider(LocalbarrierFreeTypographyProvider provides scaledTypography) {
        content()
    }
}

@Preview
@Composable
fun FontPreview(){

    Text(text = "extrabold", fontFamily = barrierFreeFontBold)

}