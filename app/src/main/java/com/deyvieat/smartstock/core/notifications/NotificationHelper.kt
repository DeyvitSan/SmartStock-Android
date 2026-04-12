package com.deyvieat.smartstock.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.deyvieat.smartstock.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    // Se llama desde SmartStockApplication al arrancar
    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannels() {
        listOf(
            NotificationChannel(
                NotificationChannels.CHANNEL_STOCK_ID,
                NotificationChannels.CHANNEL_STOCK_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = NotificationChannels.CHANNEL_STOCK_DESC },

            NotificationChannel(
                NotificationChannels.CHANNEL_SYNC_ID,
                NotificationChannels.CHANNEL_SYNC_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply { description = NotificationChannels.CHANNEL_SYNC_DESC }
        ).forEach { manager.createNotificationChannel(it) }
    }

    fun showStockAlert(productName: String, quantity: Int) {
        val notification = NotificationCompat.Builder(context,
            NotificationChannels.CHANNEL_STOCK_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("⚠️ Stock bajo")
            .setContentText("$productName tiene solo $quantity unidades")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(productName.hashCode(), notification)
    }
}