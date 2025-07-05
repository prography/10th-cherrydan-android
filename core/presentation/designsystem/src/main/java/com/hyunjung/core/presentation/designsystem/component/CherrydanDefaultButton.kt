package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

enum class CherrydanButtonStyle(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
) {
    GRAY(
        containerColor = CherrydanColors.Gray2,
        contentColor = CherrydanColors.Gray5,
        disabledContainerColor = CherrydanColors.Gray2,
        disabledContentColor = CherrydanColors.Gray5
    ),
    MAIN_PINK_2(
        containerColor = CherrydanColors.MainPink2,
        contentColor = CherrydanColors.White,
        disabledContainerColor = CherrydanColors.MainPink1,
        disabledContentColor = CherrydanColors.White
    ),
    MAIN_PINK_3(
        containerColor = CherrydanColors.MainPink3,
        contentColor = CherrydanColors.White,
        disabledContainerColor = CherrydanColors.MainPink1,
        disabledContentColor = CherrydanColors.White
    )
}

enum class CherrydanButtonShape(val cornerRadius: Dp) {
    ROUNDED_SMALL(2.dp),
    ROUNDED_MEDIUM(4.dp)
}

enum class CherrydanButtonSize {
    SMALL,
    BIG,
}

@Composable
fun CherrydanDefaultButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: CherrydanButtonStyle = CherrydanButtonStyle.GRAY,
    shape: CherrydanButtonShape = CherrydanButtonShape.ROUNDED_SMALL,
    size: CherrydanButtonSize = CherrydanButtonSize.BIG,
    enabled: Boolean = true,
    textStyle: TextStyle = CherrydanTypography.Main5_R
) {
    Box(
        modifier = when (size) {
            CherrydanButtonSize.SMALL -> {
                modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(shape.cornerRadius))
                    .background(if (enabled) style.containerColor else style.disabledContainerColor)
                    .clickable { onClick() }
            }

            CherrydanButtonSize.BIG -> {
                modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(shape.cornerRadius))
                    .background(if (enabled) style.containerColor else style.disabledContainerColor)
                    .clickable { onClick() }
                    .padding(vertical = 15.dp)
            }
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if (enabled) style.contentColor else style.disabledContentColor,
        )
    }
}

@Preview
@Composable
private fun CherrydanGrayButtonPreview() {
    CherrydanTheme {
        CherrydanDefaultButton(
            text = "Default Button",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = CherrydanButtonShape.ROUNDED_MEDIUM,
            enabled = true
        )
    }
}

@Preview
@Composable
private fun CherrydanPinkButtonPreview() {
    CherrydanTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CherrydanDefaultButton(
                text = "Pink1 Button",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                style = CherrydanButtonStyle.MAIN_PINK_2,
                shape = CherrydanButtonShape.ROUNDED_MEDIUM,
                enabled = false
            )
            CherrydanDefaultButton(
                text = "Pink2 Button",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                style = CherrydanButtonStyle.MAIN_PINK_2,
                shape = CherrydanButtonShape.ROUNDED_MEDIUM,
                enabled = true
            )
            CherrydanDefaultButton(
                text = "Pink3 Button",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                style = CherrydanButtonStyle.MAIN_PINK_3,
                shape = CherrydanButtonShape.ROUNDED_MEDIUM,
                enabled = true
            )
        }
    }
}

@Preview
@Composable
private fun CherrydanSmallButtonPreview() {
    CherrydanTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CherrydanDefaultButton(
                text = "Gray Button",
                onClick = {},
                modifier = Modifier,
                style = CherrydanButtonStyle.GRAY,
                shape = CherrydanButtonShape.ROUNDED_SMALL,
                size = CherrydanButtonSize.SMALL,
                enabled = true
            )
            CherrydanDefaultButton(
                text = "Pink1 Button",
                onClick = {},
                modifier = Modifier,
                style = CherrydanButtonStyle.MAIN_PINK_2,
                shape = CherrydanButtonShape.ROUNDED_SMALL,
                size = CherrydanButtonSize.SMALL,
                enabled = false
            )
            CherrydanDefaultButton(
                text = "Pink2 Button",
                onClick = {},
                modifier = Modifier,
                style = CherrydanButtonStyle.MAIN_PINK_2,
                shape = CherrydanButtonShape.ROUNDED_SMALL,
                size = CherrydanButtonSize.SMALL,
                enabled = true
            )
            CherrydanDefaultButton(
                text = "Pink3 Button",
                onClick = {},
                modifier = Modifier,
                style = CherrydanButtonStyle.MAIN_PINK_3,
                shape = CherrydanButtonShape.ROUNDED_SMALL,
                size = CherrydanButtonSize.SMALL,
                enabled = true
            )
        }
    }
}