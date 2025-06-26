package com.hyunjung.home.presentation.my_page

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    Text(
        text = "마이페이지",
        style = CherrydanTypography.Title1,
        color = CherrydanColors.Black
    )
}