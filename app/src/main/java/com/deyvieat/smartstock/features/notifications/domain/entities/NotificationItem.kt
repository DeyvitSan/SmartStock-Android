package com.deyvieat.smartstock.features.notifications.domain.entities

data class NotificationItem(
    val id: Int,
    val productName: String,
    val quantity : Int,
    val timestamp: Long = System.currentTimeMillis()
    )