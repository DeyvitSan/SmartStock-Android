package com.deyvieat.smartstock.features.inventory.di

import com.deyvieat.smartstock.features.inventory.data.repository.InventoryRepositoryImpl
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InventoryRepositoryModule {

    @Binds
    abstract fun bindInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository
}
