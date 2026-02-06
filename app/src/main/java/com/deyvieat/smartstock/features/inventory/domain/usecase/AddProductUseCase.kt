package com.deyvieat.smartstock.features.inventory.domain.usecase

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository

class AddProductUseCase(private val repository: InventoryRepository) {
    suspend operator fun invoke(product: Product): GenericResponse {
        return repository.addProduct(product)
    }
}