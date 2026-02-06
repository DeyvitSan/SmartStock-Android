package com.deyvieat.smartstock

import android.app.Application
import com.deyvieat.smartstock.core.di.AppContainer

class SmartStockApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}