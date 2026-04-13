package com.deyvieat.smartstock.features.notifications.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import com.deyvieat.smartstock.features.notifications.domain.entities.NotificationItem

@Singleton
class NotificationsRepository @Inject constructor() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    fun addNotification(productName: String, quantity: Int) {
        _notifications.update { current ->
            // Solo agrega si no existe ya una alerta para ese producto
            if (current.any { it.productName == productName }) return
            val new = NotificationItem(
                id = current.size + 1,
                productName = productName,
                quantity = quantity
            )
            listOf(new) + current
        }
    }
}