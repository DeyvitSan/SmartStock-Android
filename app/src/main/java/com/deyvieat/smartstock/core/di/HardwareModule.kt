package com.deyvieat.smartstock.core.di

import com.deyvieat.smartstock.core.hardware.VibrationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HardwareModule {

    @Provides
    @Singleton
    fun provideVibrationManager(
        @ApplicationContext context: Context
    ): VibrationManager = VibrationManager(context)
}