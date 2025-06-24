package com.hyunjung.auth.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.ui.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CherrydanColors.PointBeige),
        contentAlignment = Alignment.Center
    ) {
        CherrydanLogoVertical()
    }
}

@Composable
private fun CherrydanLogoVertical() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.padding(7.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.splash_title),
            style = CherrydanTypography.Title1,
            color = CherrydanColors.Black
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    CherrydanTheme {
        SplashScreen(
            onSplashFinished = { /* No action needed for preview */ }
        )
    }
}