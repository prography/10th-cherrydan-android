package com.hyunjung.notification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjung.core.presentation.designsystem.BackIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.CircleSelectedIcon
import com.hyunjung.core.presentation.designsystem.CircleUnselectedIcon
import com.hyunjung.core.presentation.designsystem.TrashIcon
import com.hyunjung.core.presentation.designsystem.component.CherrydanFixedTabRow
import com.hyunjung.core.presentation.designsystem.component.CherrydanTab
import com.hyunjung.core.presentation.designsystem.component.CherrydanTopAppBar
import com.hyunjung.core.presentation.designsystem.component.TopBarIconButton
import com.hyunjung.notification.presentation.component.AlertType
import com.hyunjung.notification.presentation.component.NotificationActiveToggleItem
import com.hyunjung.notification.presentation.component.NotificationToggleItem

@Composable
fun NotificationScreen(
    onBackPressed: () -> Unit = {},
    onDeletePressed: (deletedItems: List<NotificationItemData>) -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var notificationItems by remember { mutableStateOf(getSampleNotifications()) }
    var isDeleteMode by remember { mutableStateOf(false) }
    val isInPreview = LocalInspectionMode.current

    val allSelected = notificationItems.all { it.isSelected }
    val hasAnySelected = notificationItems.any { it.isSelected }

    val toggleItemSelection: (Int) -> Unit = { index ->
        notificationItems = notificationItems.mapIndexed { idx, item ->
            if (idx == index) {
                item.copy(isSelected = !item.isSelected)
            } else {
                item
            }
        }
    }

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
                    NotificationScreenActions(
                        isDeleteMode = isDeleteMode,
                        onDeleteModeToggle = { isDeleteMode = !isDeleteMode }
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
                        onClick = onBackPressed
                    )
                },
                actions = {
                    if (!isDeleteMode) {
                        TopBarIconButton(
                            imageVector = TrashIcon,
                            contentDescription = "Delete",
                            onClick = { isDeleteMode = true }
                        )
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = CherrydanColors.White)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = CherrydanColors.PointBeige
            )
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
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 모두선택 / 읽음처리 또는 삭제 행
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clickable {
                        notificationItems = notificationItems.map { item ->
                            item.copy(isSelected = !allSelected)
                        }
                    }
                    .padding(end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (allSelected) CircleSelectedIcon else CircleUnselectedIcon,
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isInPreview) "모두 선택" else stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_all_select),
                    style = CherrydanTypography.Main4_R,
                    color = CherrydanColors.Black
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            if (isDeleteMode) {
                // 삭제 모드: "삭제 | 취소" 표시
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isInPreview) "삭제" else stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_delete),
                        style = CherrydanTypography.Main4_B.copy(fontSize = 14.sp),
                        color = CherrydanColors.MainPink3,
                        modifier = Modifier
                            .clickable(enabled = hasAnySelected) {
                                // 선택된 항목들을 삭제
                                val itemsToDelete = notificationItems.filter { it.isSelected }
                                notificationItems = notificationItems.filter { !it.isSelected }
                                isDeleteMode = false
                                onDeletePressed(itemsToDelete)
                            }
                            .padding(end = 12.dp)
                    )

                    Text(
                        text = "|",
                        style = CherrydanTypography.Main5_R,
                        color = CherrydanColors.Gray4
                    )

                    Text(
                        text = if (isInPreview) "취소" else stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_cancel),
                        style = CherrydanTypography.Main4_B.copy(fontSize = 14.sp),
                        color = CherrydanColors.Black,
                        modifier = Modifier
                            .clickable {
                                // 삭제 모드 취소 및 선택 해제
                                notificationItems =
                                    notificationItems.map { it.copy(isSelected = false) }
                                isDeleteMode = false
                            }
                            .padding(start = 12.dp)
                    )
                }
            } else {
                // 일반 모드: "읽음" 표시
                Text(
                    text = if (isInPreview) "읽음" else stringResource(id = com.hyunjung.core.presentation.ui.R.string.notification_read),
                    style = CherrydanTypography.Main4_R,
                    color = if (hasAnySelected) CherrydanColors.Black else CherrydanColors.Gray4,
                    modifier = Modifier.clickable(enabled = hasAnySelected) {
                        // 선택된 항목들을 읽음 처리
                        notificationItems = notificationItems.map { item ->
                            if (item.isSelected) {
                                item.copy(
                                    isRead = true,
                                    isSelected = false,
                                    hasHighPriority = false
                                )
                            } else {
                                item
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 알림 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            itemsIndexed(notificationItems) { index, item ->
                if (selectedTabIndex == 0) { // 활동 탭
                    val annotatedContent = createAnnotatedString(item.content)

                    NotificationActiveToggleItem(
                        alertType = AlertType.VISITED,
                        content = annotatedContent,
                        time = System.currentTimeMillis(),
                        selected = item.isSelected,
                        showBadge = item.hasHighPriority,
                        onClick = { toggleItemSelection(index) },
                        showDivider = index != notificationItems.lastIndex,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                } else { // 맞춤형 탭
                    NotificationToggleItem(
                        selected = item.isSelected,
                        showBadge = item.hasHighPriority,
                        onClick = {
                            // 개별 아이템 선택 토글
                            notificationItems =
                                notificationItems.mapIndexed { idx, notificationItem ->
                                    if (idx == index) {
                                        notificationItem.copy(isSelected = !notificationItem.isSelected)
                                    } else {
                                        notificationItem
                                    }
                                }
                        },
                        showDivider = index != notificationItems.lastIndex,
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
private fun NotificationScreenActions(
    isDeleteMode: Boolean,
    onDeleteModeToggle: () -> Unit
) {
    if (!isDeleteMode) {
        TopBarIconButton(
            imageVector = TrashIcon,
            contentDescription = "Delete",
            onClick = onDeleteModeToggle
        )
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

data class NotificationItemData(
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val isSelected: Boolean = false,
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