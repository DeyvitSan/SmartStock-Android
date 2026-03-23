package com.deyvieat.smartstock.features.inventory.domain.usecases

import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = repository.deleteProduct(id)
}
