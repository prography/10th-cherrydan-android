package com.hyunjung.notification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjung.notification.presentation.component.AlertType
import com.hyunjung.auth.presentation.LocalCherrydanContentColor
import com.hyunjung.notification.presentation.component.NotificationActiveToggleItem
import com.hyunjung.notification.presentation.component.NotificationToggleItem
import com.hyunjung.core.presentation.designsystem.BackIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.CircleUnselectedIcon
import com.hyunjung.core.presentation.designsystem.TrashIcon
import com.hyunjung.core.presentation.designsystem.component.CherrydanFixedTabRow
import com.hyunjung.core.presentation.designsystem.component.CherrydanTab
import com.hyunjung.core.presentation.designsystem.component.CherrydanTopAppBar
import com.hyunjung.core.presentation.designsystem.component.TopBarIconButton

@Composable
fun NotificationScreen(
    onBackPressed: () -> Unit = {},
    onDeletePressed: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val notificationItems = remember { getSampleNotifications() }
    val isInPreview = LocalInspectionMode.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CherrydanColors.White)
    ) {
        if (isInPreview) {
            CherrydanTopAppBar(
                title = "알림",
                navigationIcon = {
                    TopBarIconButton(
                        imageVector = BackIcon,
                        contentDescription = "Back",
                        onClick = {}
                    )
                },
                actions = {
                    TopBarIconButton(
                        imageVector = TrashIcon,
                        contentDescription = "Delete",
                        onClick = {}
                    )
                }
            )
        } else {
            CherrydanTopAppBar(
                title = stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_title),
                navigationIcon = {
                    TopBarIconButton(
                        imageVector = BackIcon,
                        contentDescription = "Back",
                        onClick = {}
                    )
                },
                actions = {
                    TopBarIconButton(
                        imageVector = TrashIcon,
                        contentDescription = "Delete",
                        onClick = {}
                    )
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CherrydanColors.White)
        ) {

            CherrydanFixedTabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                val tabs = if (isInPreview) {
                    listOf("활동", "맞춤형")
                } else {
                    listOf(
                        stringResource(com.hyunjung.core.presentation.ui.R.string.notification_activity),
                        stringResource(com.hyunjung.core.presentation.ui.R.string.notification_keyword)
                    )
                }

                tabs.forEachIndexed { index, title ->
                    val selected = index == selectedTabIndex
                    CherrydanTab(
                        selected = selected,
                        onClick = { selectedTabIndex = index }
                    ) {
                        Text(
                            text = title,
                            color = if (selected) CherrydanColors.MainPink3 else CherrydanColors.Gray4,
                            style = if (selected) {
                                CherrydanTypography.Main3_B
                            } else {
                                CherrydanTypography.Main3_R
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = CherrydanColors.PointBeige
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = CircleUnselectedIcon,
                contentDescription = "All Select",
                tint = LocalCherrydanContentColor.current,
                modifier = Modifier
                    .size(24.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_all_select),
                style = CherrydanTypography.Main4_R,
                color = CherrydanColors.Black,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_read),
                style = CherrydanTypography.Main4_R,
                color = CherrydanColors.Black
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 알림 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(notificationItems) { item ->
                if (selectedTabIndex == 0) { // 활동 탭
                    // NotificationActiveToggleItem 사용
                    val annotatedContent = createAnnotatedString(item.content)

                    NotificationActiveToggleItem(
                        alertType = AlertType.VISITED,
                        content = annotatedContent,
                        time = System.currentTimeMillis(),
                        selected = !item.isRead,
                        showBadge = item.hasHighPriority,
                        onClick = { /* TODO: 알림 클릭 시 동작 */ },
                        showDivider = item.id != (notificationItems.lastOrNull()?.id ?: 0),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else { // 맞춤형 탭
                    // 일반 NotificationToggleItem 사용
                    NotificationToggleItem(
                        selected = !item.isRead,
                        showBadge = item.hasHighPriority,
                        onClick = { /* TODO: 알림 클릭 시 동작 */ },
                        showDivider = item.id != (notificationItems.lastOrNull()?.id ?: 0),
                        paddingValues = PaddingValues(vertical = 8.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = CherrydanTypography.Main5_B,
                                color = CherrydanColors.Gray4
                            )

                            if (item.content.isNotEmpty()) {
                                Text(
                                    text = item.content,
                                    style = CherrydanTypography.Main5_R,
                                    color = CherrydanColors.Gray4
                                )
                            }

                            if (item.date.isNotEmpty()) {
                                Text(
                                    text = item.date,
                                    style = CherrydanTypography.Main6_R,
                                    color = CherrydanColors.Gray4
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun createAnnotatedString(content: String) = buildAnnotatedString {
    append(content)

    val boldPart = "D-3 [목요일] 리자이드 양주점"
    val startIndex = content.indexOf(boldPart)
    if (startIndex >= 0) {
        addStyle(
            style = SpanStyle(fontWeight = FontWeight.Bold),
            start = startIndex,
            end = startIndex + boldPart.length
        )
    }
}

@Composable
private fun NotificationItemProduction(
    item: NotificationItemData,
    modifier: Modifier = Modifier
) {
    val isInPreview = LocalInspectionMode.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        // 알림 내용
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = if (item.isRead) FontWeight.Normal else FontWeight.Medium,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (item.hasHighPriority) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(CherrydanColors.MainPink3)
                        )
                    }
                    Text(
                        text = if (isInPreview) "읽음" else "읽음", // 프리뷰에서는 하드코딩
                        fontSize = 12.sp,
                        color = Color(0xFF9E9E9E)
                    )
                }
            }

            if (item.content.isNotEmpty()) {
                Text(
                    text = item.content,
                    fontSize = 14.sp,
                    color = Color(0xFF757575),
                    lineHeight = 20.sp
                )
            }

            if (item.date.isNotEmpty()) {
                Text(
                    text = item.date,
                    fontSize = 12.sp,
                    color = Color(0xFFBDBDBD)
                )
            }
        }
    }

    // 구분선
    if (item.id != 5) {
        HorizontalDivider(
            modifier = Modifier.padding(start = 36.dp),
            color = Color(0xFFF5F5F5),
            thickness = 1.dp
        )
    }
}

data class NotificationItemData(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val isRead: Boolean = false,
    val hasHighPriority: Boolean = false
)

private fun getSampleNotifications(): List<NotificationItemData> {
    return listOf(
        NotificationItemData(
            id = 1,
            title = "모든 선택",
            content = "집가고 싶다. 집가고 싶다. 집가고 싶다. 집가고 싶다.",
            date = "2025.05.05",
            isRead = false
        ),
        NotificationItemData(
            id = 2,
            title = "병원방문",
            content = "D-3 [목요일] 리자이드 양주점, 피드&월스 방문리의 3일 남았습니다.",
            date = "2025.05.05",
            isRead = false,
            hasHighPriority = true
        ),
        NotificationItemData(
            id = 3,
            title = "병원방문",
            content = "D-3 [목요일] 리자이드 양주점, 피드&월스 방문리의 3일 남았습니다.",
            date = "2025.05.05",
            isRead = false
        ),
        NotificationItemData(
            id = 4,
            title = "병원방문",
            content = "D-3 [목요일] 리자이드 양주점, 피드&월스 방문리의 3일 남았습니다.",
            date = "2025.05.05",
            isRead = false
        ),
        NotificationItemData(
            id = 5,
            title = "병원방문",
            content = "D-3 [목요일] 리자이드 양주점, 피드&월스 방문리의 3일 남았습니다.",
            date = "2025.05.05",
            isRead = false,
            hasHighPriority = true
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun NotificationScreenPreview() {
    CherrydanTheme {
        NotificationScreen()
    }
}