package com.deyvieat.smartstock.features.inventory.domain.usecases

import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val repository: InventoryRepository) {
    suspend operator fun invoke(product: Product): Result<Unit> = repository.updateProduct(product)
}
