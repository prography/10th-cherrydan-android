package com.hyunjung.feature.notification.model

import androidx.compose.ui.graphics.Color
import com.hyunjung.core.presentation.designsystem.CherrydanColors

enum class NotificationPriority(val color: Color) {
    HIGH(CherrydanColors.MainPink3),
    NORMAL(CherrydanColors.Gray4)
}