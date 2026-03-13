package com.deyvieat.smartstock.features.inventory.domain.usecase

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository

class DeleteProductUseCase(private val repository: InventoryRepository) {
    suspend operator fun invoke(id: Int): GenericResponse {
        return repository.deleteProduct(id)
    }
}