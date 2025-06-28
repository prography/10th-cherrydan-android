package com.hyunjung.notification.presentation.viewmodel

import androidx.compose.ui.graphics.Color
import com.hyunjung.core.presentation.designsystem.CherrydanColors

data class NotificationItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val date: String,
    val isRead: Boolean = false,
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val content: String
)

enum class NotificationPriority(val color: Color) {
    HIGH(CherrydanColors.MainPink3),
    NORMAL(CherrydanColors.Gray4)
}

enum class NotificationTabType(val title: String) {
    ALL("활동"),
    READ("맞춤형")
}