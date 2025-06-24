package com.hyunjung.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInButton
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInButtonType
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInOutlinedButton
import com.hyunjung.core.presentation.ui.R

@Composable
fun LogInScreenRoot(
    onKakaoLogInClick: () -> Unit,
    onNaverLogInClick: () -> Unit,
    onGoogleLogInClick: () -> Unit,
) {
    LogInScreen(
        onAction = { action ->
            when (action) {
                LogInAction.OnKakaoLogInClick -> {
                    onKakaoLogInClick()
                }

                LogInAction.OnNaverLogInClick -> {
                    onNaverLogInClick()
                }

                LogInAction.OnGoogleLogInClick -> {
                    onGoogleLogInClick()
                }
            }
        }
    )
}

@Composable
fun LogInScreen(
    onAction: (LogInAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CherrydanColors.PointBeige),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CherrydanLogoHorizontal()
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.login_start),
                style = CherrydanTypography.Main4_R,
                color = CherrydanColors.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            CherrydanLogInButton(
                text = stringResource(id = R.string.login_kakao),
                isLoading = false,
                logInButtonType = CherrydanLogInButtonType.Kakao,
                onClick = {
                    onAction(LogInAction.OnKakaoLogInClick)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CherrydanLogInButton(
                text = stringResource(id = R.string.login_naver),
                isLoading = false,
                logInButtonType = CherrydanLogInButtonType.Naver,
                onClick = {
                    onAction(LogInAction.OnNaverLogInClick)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CherrydanLogInOutlinedButton(
                text = stringResource(id = R.string.login_google),
                isLoading = false,
                onClick = {
                    onAction(LogInAction.OnGoogleLogInClick)
                },
            )
            Spacer(modifier = Modifier.height(44.dp))
        }
    }
}

@Composable
private fun CherrydanLogoHorizontal() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(120.dp),
            painter = painterResource(id = R.drawable.img_logo_background),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = R.string.login_title),
            style = CherrydanTypography.Title1,
            color = CherrydanColors.Black
        )
    }
}

@Preview
@Composable
private fun LogInScreenPreview() {
    CherrydanTheme {
        LogInScreen(
            onAction = {}
        )
    }
}