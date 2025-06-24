package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.KakaoIcon
import com.hyunjung.core.presentation.designsystem.NaverIcon
import com.hyunjung.core.presentation.designsystem.R

enum class CherrydanLogInButtonType {
    Kakao,
    Naver
}

data class CherrydanButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
)

object CherrydanButtonColorsDefaults {
    val Kakao = CherrydanButtonColors(
        containerColor = CherrydanColors.KakaoYellow,
        contentColor = CherrydanColors.Black,
        disabledContainerColor = CherrydanColors.Gray3,
        disabledContentColor = CherrydanColors.Black
    )

    val Naver = CherrydanButtonColors(
        containerColor = CherrydanColors.NaverGreen,
        contentColor = CherrydanColors.White,
        disabledContainerColor = CherrydanColors.Gray3,
        disabledContentColor = CherrydanColors.White
    )

    val Google = CherrydanButtonColors(
        containerColor = CherrydanColors.White,
        contentColor = CherrydanColors.Black,
        disabledContainerColor = CherrydanColors.Gray3,
        disabledContentColor = CherrydanColors.Black
    )
}

@Composable
fun CherrydanLogInButton(
    text: String,
    isLoading: Boolean,
    logInButtonType: CherrydanLogInButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val buttonColors = when (logInButtonType) {
        CherrydanLogInButtonType.Kakao -> CherrydanButtonColorsDefaults.Kakao
        CherrydanLogInButtonType.Naver -> CherrydanButtonColorsDefaults.Naver
    }

    val buttonIcon = when (logInButtonType) {
        CherrydanLogInButtonType.Kakao -> KakaoIcon
        CherrydanLogInButtonType.Naver -> NaverIcon
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColors.containerColor,
            contentColor = buttonColors.contentColor,
            disabledContainerColor = buttonColors.disabledContainerColor,
            disabledContentColor = buttonColors.disabledContentColor
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp
            )
            Icon(
                imageVector = buttonIcon,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .alpha(if (isLoading) 0f else 1f)
                    .padding(start = 12.dp),
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                color = CherrydanColors.Black,
                style = CherrydanTypography.Main3_R
            )
        }
    }
}

@Composable
fun CherrydanLogInOutlinedButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .background(CherrydanColors.White)
            .border(width = 1.dp, color = CherrydanColors.Gray3, shape = RoundedCornerShape(4.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .alpha(if (isLoading) 0f else 1f)
                    .padding(start = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_google),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                color = CherrydanColors.Black,
                style = CherrydanTypography.Main3_R
            )
        }
    }
}

@Preview
@Composable
private fun CherrydanKakaoLogInButtonPreview() {
    CherrydanTheme {
        CherrydanLogInButton(
            text = "카카오로 로그인",
            isLoading = false,
            logInButtonType = CherrydanLogInButtonType.Kakao
        ) { }
    }
}

@Preview
@Composable
private fun CherrydanNaverLogInButtonPreview() {
    CherrydanTheme {
        CherrydanLogInButton(
            text = "네이버로 로그인",
            isLoading = false,
            logInButtonType = CherrydanLogInButtonType.Naver
        ) { }
    }
}

@Preview
@Composable
private fun CherrydanLogInOutlinedButtonPreview() {
    CherrydanTheme() {
        CherrydanLogInOutlinedButton(
            text = "Google로 로그인",
            isLoading = false
        ) { }
    }
}
