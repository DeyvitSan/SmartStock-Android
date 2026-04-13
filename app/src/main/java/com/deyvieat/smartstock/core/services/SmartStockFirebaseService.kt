package com.deyvieat.smartstock.core.services

import com.deyvieat.smartstock.core.notifications.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.deyvieat.smartstock.features.notifications.domain.repository.NotificationsRepository
@AndroidEntryPoint
class SmartStockFirebaseService : FirebaseMessagingService() {

    @Inject lateinit var notificationHelper: NotificationHelper
    @Inject lateinit var notificationsRepository: NotificationsRepository

    // Se llama cuando llega una push notification
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val productName = message.data["productName"] ?: "Producto"
        val quantity    = message.data["quantity"]?.toIntOrNull() ?: 0

        notificationHelper.showStockAlert(productName, quantity)
        notificationsRepository.addNotification(productName, quantity)
    }

    // Se llama cuando FCM renueva el token del dispositivo
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        android.util.Log.d("FCM", "Token renovado: $token")

    }
}