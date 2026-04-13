package com.deyvieat.smartstock.features.notifications.presentation.screens

import com.deyvieat.smartstock.features.notifications.domain.entities.NotificationItem

data class NotificationsUiState(
    val notifications: List<NotificationItem> = emptyList(),
    val isEmpty: Boolean = true
)