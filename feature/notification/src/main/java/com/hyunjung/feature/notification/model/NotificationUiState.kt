package com.hyunjung.feature.notification.model

import com.hyunjung.feature.notification.viewmodel.NotificationItem
import com.hyunjung.feature.notification.viewmodel.NotificationTabType

data class NotificationUiState(
    val notifications: List<NotificationItem> = emptyList(),
    val selectedTab: NotificationTabType = NotificationTabType.ALL,
    val selectedFilter: String = "전체",
    val isLoading: Boolean = false,
    val showMenu: Boolean = false
)