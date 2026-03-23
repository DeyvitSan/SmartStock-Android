package com.deyvieat.smartstock.features.scanner.domain.usecases

import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// UseCase del Scanner: recibe el texto del QR y busca en Room (local, sin red)
class SearchProductByCodeUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return try {
            val allProducts = repository.getProductsStream().first()
            val matched = allProducts.filter { it.name.contains(query, ignoreCase = true) }
            if (matched.isNotEmpty()) Result.success(matched)
            else Result.failure(Exception("Sin resultados para: $query"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
