package com.hyunjung.feature.notification.model

data class NotificationUiState(
    val notifications: List<NotificationItem> = emptyList(),
    val selectedTab: NotificationTabType = NotificationTabType.ALL,
    val selectedFilter: String = "전체",
    val isLoading: Boolean = false,
    val showMenu: Boolean = false
)