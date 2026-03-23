package com.deyvieat.smartstock.features.inventory.domain.usecases

import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: InventoryRepository) {
    operator fun invoke(): Flow<List<Product>> = repository.getProductsStream()
    suspend fun sync() = repository.syncProducts()
}