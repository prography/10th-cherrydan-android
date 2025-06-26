package com.hyunjung.home.presentation.my_campaigns

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun MyCampaignsScreen(modifier: Modifier = Modifier) {
    Text(
        text = "내 체험단 화면",
        style = CherrydanTypography.Title1,
        color = CherrydanColors.Black
    )
}