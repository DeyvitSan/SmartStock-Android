package com.deyvieat.smartstock.features.dashboard.domain.usecases

import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDashboardDataUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    fun getAllProducts(): Flow<List<Product>> = repository.getProductsStream()
    fun getLowStockProducts(threshold: Int = 5): Flow<List<Product>> = repository.getLowStockStream(threshold)
}
