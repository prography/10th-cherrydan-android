package com.hyunjung.home.presentation.notice

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun NoticeScreen(modifier: Modifier = Modifier) {
    Text(
        text = "체리단 소식",
        style = CherrydanTypography.Title1,
        color = CherrydanColors.Black
    )
}