package com.deyvieat.smartstock.features.inventory.domain.repository

import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun getProductsStream(): Flow<List<Product>>
    fun getLowStockStream(threshold: Int = 5): Flow<List<Product>>
    suspend fun syncProducts()
    suspend fun addProduct(product: Product): Result<Unit>
    suspend fun updateProduct(product: Product): Result<Unit>
    suspend fun deleteProduct(id: Int): Result<Unit>
    suspend fun getLowStockList(threshold: Int = 5): List<Product>
}