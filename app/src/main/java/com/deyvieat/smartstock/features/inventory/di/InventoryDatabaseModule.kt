package com.deyvieat.smartstock.features.inventory.di

import com.deyvieat.smartstock.core.database.SmartStockDatabase
import com.deyvieat.smartstock.features.inventory.data.datasources.local.dao.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InventoryDatabaseModule {

    @Provides
    @Singleton
    fun provideProductDao(db: SmartStockDatabase): ProductDao = db.productDao()
}
