package com.hyunjung.home.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun HomeScreenRoot(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier)
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Text(
        text = "홈 화면",
        style = CherrydanTypography.Title1,
        color = CherrydanColors.Black
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}