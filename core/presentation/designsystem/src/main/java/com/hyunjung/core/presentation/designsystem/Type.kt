package com.hyunjung.core.presentation.designsystem

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val GangwonEduAll = FontFamily(
    Font(
        resId = R.font.gangwon_edu_all_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.gangwon_edu_all_bold,
        weight = FontWeight.Bold
    )
)

val HappinessSans = FontFamily(
    Font(
        resId = R.font.happiness_sans_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.happiness_sans_bold,
        weight = FontWeight.Bold
    )
)

object CherrydanTypography {
    val Title1 = TextStyle(
        fontFamily = GangwonEduAll,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        lineHeight = 39.2.sp,
        letterSpacing = 0.sp
    )

    val Title2 = TextStyle(
        fontFamily = GangwonEduAll,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 33.6.sp,
        letterSpacing = 0.sp
    )

    val Title3 = TextStyle(
        fontFamily = GangwonEduAll,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
        letterSpacing = 0.sp
    )

    val Title4 = TextStyle(
        fontFamily = GangwonEduAll,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp,
        letterSpacing = 0.sp
    )

    val Title5 = TextStyle(
        fontFamily = GangwonEduAll,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 16.8.sp,
        letterSpacing = 0.sp
    )

    val Main1_B = TextStyle(
        fontSize = 20.sp,
        lineHeight = 30.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Bold
    )

    val Main2_B = TextStyle(
        fontSize = 18.sp,
        lineHeight = 27.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Bold
    )

    val Main3_B = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Bold
    )

    val Main4_B = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Bold
    )

    val Main5_B = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Bold
    )

    val Main1_R = TextStyle(
        fontSize = 20.sp,
        lineHeight = 30.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )

    val Main2_R = TextStyle(
        fontSize = 18.sp,
        lineHeight = 27.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )

    val Main3_R = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )

    val Main4_R = TextStyle(
        fontSize = 14.sp,
        lineHeight = 21.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )

    val Main5_R = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )

    val Main6_R = TextStyle(
        fontSize = 10.sp,
        lineHeight = 15.sp,
        fontFamily = HappinessSans,
        fontWeight = FontWeight.Normal
    )
}