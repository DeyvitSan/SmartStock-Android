package com.deyvieat.smartstock.core.di

import com.deyvieat.smartstock.core.hardware.VibrationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import com.deyvieat.smartstock.core.notifications.NotificationHelper
import com.deyvieat.smartstock.core.services.TokenApi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HardwareModule {

    @Provides
    @Singleton
    fun provideVibrationManager(
        @ApplicationContext context: Context
    ): VibrationManager = VibrationManager(context)

    @Provides
    @Singleton
    fun provideNotificationHelper(
        @ApplicationContext context: Context
    ): NotificationHelper = NotificationHelper(context)

    @Provides
    @Singleton
    fun provideTokenApi(@SmartStockRetrofit retrofit: Retrofit): TokenApi =
        retrofit.create(TokenApi::class.java)
}