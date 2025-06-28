package com.hyunjung.notification.presentation.model

import com.hyunjung.notification.presentation.viewmodel.NotificationItem
import com.hyunjung.notification.presentation.viewmodel.NotificationTabType

data class NotificationUiState(
    val notifications: List<NotificationItem> = emptyList(),
    val selectedTab: NotificationTabType = NotificationTabType.ALL,
    val selectedFilter: String = "전체",
    val isLoading: Boolean = false,
    val showMenu: Boolean = false
)