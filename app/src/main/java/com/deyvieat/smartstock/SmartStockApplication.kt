package com.deyvieat.smartstock

import android.app.Application
import android.os.Build
import com.deyvieat.smartstock.core.notifications.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SmartStockApplication : Application(){

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationHelper.createChannels()
        }
    }
}