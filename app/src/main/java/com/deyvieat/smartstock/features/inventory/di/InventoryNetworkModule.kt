package com.deyvieat.smartstock.features.inventory.di

import com.deyvieat.smartstock.core.di.SmartStockRetrofit
import com.deyvieat.smartstock.features.inventory.data.datasources.remote.api.InventoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InventoryNetworkModule {

    @Provides
    @Singleton
    fun provideInventoryApi(@SmartStockRetrofit retrofit: Retrofit): InventoryApi =
        retrofit.create(InventoryApi::class.java)
}
