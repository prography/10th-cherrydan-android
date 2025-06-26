package com.hyunjung.home.presentation.category

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun CategoryScreen(modifier: Modifier = Modifier) {
    Text(
        text = "카테고리",
        style = CherrydanTypography.Title1,
        color = CherrydanColors.Black
    )
}