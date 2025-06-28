package com.hyunjung.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.ArrowRightIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.CircleSelectedIcon
import com.hyunjung.core.presentation.designsystem.CircleUnselectedIcon
import java.text.SimpleDateFormat
import java.util.Locale

private val ContentPadding = 4.dp

enum class AlertType(
    val label: String,
) {
    VISITED("방문알림"),
}

@Composable
fun NotificationCustomToggleItem(
    content: AnnotatedString,
    selected: Boolean,
    showBadge: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NotificationToggleItem(
            selected = selected,
            showBadge = showBadge,
            onClick = onClick,
            modifier = Modifier.weight(1f),
            showDivider = showDivider,
            verticalAlignment = Alignment.CenterVertically,
            paddingValues = PaddingValues(top = 8.dp, bottom = 16.dp)
        ) {
            Text(
                text = content,
                style = CherrydanTypography.Main5_R
            )
        }
        Icon(
            imageVector = ArrowRightIcon,
            contentDescription = null,
        )
    }
}

@Composable
fun NotificationActiveToggleItem(
    alertType: AlertType,
    content: AnnotatedString,
    time: Long,
    selected: Boolean,
    showBadge: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean = false,
) {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

    NotificationToggleItem(
        selected = selected,
        showBadge = showBadge,
        onClick = onClick,
        modifier = modifier,
        showDivider = showDivider,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = alertType.label,
                style = CherrydanTypography.Main5_B,
                color = CherrydanColors.Gray5
            )
            Text(
                text = content,
                style = CherrydanTypography.Main5_R
            )
            Text(
                text = formatter.format(time),
                style = CherrydanTypography.Main5_R,
                color = CherrydanColors.Gray4
            )
        }
    }
}

@Composable
fun NotificationToggleItem(
    selected: Boolean,
    showBadge: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showDivider: Boolean,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit,
) {
    val frontIcon = if (selected) CircleSelectedIcon else CircleUnselectedIcon
    Box(
        modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .drawBehind {
                if (showDivider) {
                    drawLine(
                        color = CherrydanColors.Gray2,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
            .padding(paddingValues),
    ) {
        Row(verticalAlignment = verticalAlignment) {
            Icon(
                imageVector = frontIcon,
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = ContentPadding,
                        end = ContentPadding,
                        bottom = 8.dp
                    )
            ) {
                content()
            }
        }
        if (showBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(8.dp)
                    .background(color = CherrydanColors.MainPink2, shape = CircleShape)
                    .clip(CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationActiveToggleItemPreview() {
    val fullText = "D-3 [양주] 리치마트 양주점_피드&릴스 방문일이 3일 남았습니다."
    val boldText = "D-3 [양주] 리치마트 양주점_피드"

    val startIndex = fullText.indexOf(boldText)
    val endIndex = startIndex + boldText.length

    val annotatedString = buildAnnotatedString {
        append(fullText)
        addStyle(
            style = CherrydanTypography.Main5_B.toSpanStyle(),
            start = startIndex,
            end = endIndex
        )
    }

    CherrydanTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationActiveToggleItem(
                alertType = AlertType.VISITED,
                content = annotatedString,
                time = 1724892093000,
                selected = true,
                showBadge = true,
                onClick = {},
                showDivider = true
            )
            NotificationActiveToggleItem(
                alertType = AlertType.VISITED,
                content = annotatedString,
                time = 1724892093000,
                selected = false,
                showBadge = false,
                onClick = {},
                showDivider = true
            )

            NotificationActiveToggleItem(
                alertType = AlertType.VISITED,
                content = annotatedString,
                time = 1724892093000,
                selected = false,
                showBadge = true,
                onClick = {},
                showDivider = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationCustomToggleItemPreview() {
    val fullText = "딸기케이크 캠페인이 23건 등록되었어요. 지금 바로 확인해 보세요."
    val annotatedString = buildAnnotatedString {
        append(fullText)

        // 딸기케이크 스타일 적용
        val cakeText = "딸기케이크"
        val cakeStart = fullText.indexOf(cakeText)
        val cakeEnd = cakeStart + cakeText.length
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = cakeStart,
            end = cakeEnd
        )

        // 23건 스타일 적용
        val countText = "23건"
        val countStart = fullText.indexOf(countText)
        val countEnd = countStart + countText.length
        addStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = CherrydanColors.MainPink2
            ),
            start = countStart,
            end = countEnd
        )
    }

    CherrydanTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationCustomToggleItem(
                content = annotatedString,
                selected = true,
                showBadge = true,
                onClick = {},
            )
            NotificationCustomToggleItem(
                content = annotatedString,
                selected = false,
                showBadge = true,
                onClick = {},
            )
        }
    }
}