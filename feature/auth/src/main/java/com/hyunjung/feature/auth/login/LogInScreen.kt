package com.hyunjung.feature.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyunjung.core.model.SocialType
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInButton
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInButtonType
import com.hyunjung.core.presentation.designsystem.component.CherrydanLogInOutlinedButton
import com.hyunjung.core.presentation.ui.R
import org.koin.androidx.compose.koinViewModel

// todo : LoginUiState에 따른 화면 만들기
@Composable
fun LogInScreenRoot(
    onKakaoLogInClick: () -> Unit,
    onNaverLogInClick: () -> Unit,
    onGoogleLogInClick: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LogInViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // 로그인 성공 시 네비게이션
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
    }

    // 에러 메시지 표시
    LaunchedEffect(uiState) {
        val error = (uiState as? LoginUiState.Error)?.error
        error?.toString()?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    val localContext = LocalContext.current

    LogInScreen(
        state = uiState,
        onLogIn = { socialType ->
            viewModel.login(localContext, socialType)
        }
    )
}

@Composable
fun LogInScreen(
    state: LoginUiState,
    onLogIn: (SocialType) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CherrydanColors.PointBeige)
            .windowInsetsPadding(WindowInsets.systemBars),
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
                isLoading = state == LoginUiState.Loading,
                logInButtonType = CherrydanLogInButtonType.Kakao,
                onClick = { onLogIn(SocialType.KAKAO) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CherrydanLogInButton(
                text = stringResource(id = R.string.login_naver),
                isLoading = state == LoginUiState.Loading,
                logInButtonType = CherrydanLogInButtonType.Naver,
                onClick = { onLogIn(SocialType.NAVER) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CherrydanLogInOutlinedButton(
                text = stringResource(id = R.string.login_google),
                isLoading = state == LoginUiState.Loading,
                onClick = { onLogIn(SocialType.GOOGLE) },
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
            state = LoginUiState.Loading,
            onLogIn = {}
        )
    }
}