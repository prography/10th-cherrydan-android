package com.hyunjung.feature.notification.model

data class NotificationItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val date: String,
    val isRead: Boolean = false,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val content: String
)