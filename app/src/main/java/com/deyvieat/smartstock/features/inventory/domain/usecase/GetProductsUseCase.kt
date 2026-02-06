package com.deyvieat.smartstock.features.inventory.domain.usecase

import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository

class GetProductsUseCase(private val repository: InventoryRepository) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}