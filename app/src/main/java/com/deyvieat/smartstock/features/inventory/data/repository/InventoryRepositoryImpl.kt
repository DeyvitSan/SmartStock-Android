package com.deyvieat.smartstock.features.inventory.data.repository

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.core.network.InventoryApiService
import com.deyvieat.smartstock.features.inventory.data.mapper.toDomain
import com.deyvieat.smartstock.features.inventory.data.mapper.toDto
import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository

class InventoryRepositoryImpl(private val apiService: InventoryApiService) : InventoryRepository {

    override suspend fun getProducts(): List<Product> {
        // Obtenemos DTOs de la API y los convertimos a Domain
        return apiService.getProducts().map { it.toDomain() }
    }

    override suspend fun addProduct(product: Product): GenericResponse {
        // Convertimos Domain a DTO (User ID 1 temporal) y enviamos
        return apiService.addProduct(product.toDto(userId = 1))
    }

    override suspend fun updateProduct(product: Product): GenericResponse {
        // La API pide ID en la URL y el objeto en el body
        return apiService.updateProduct(product.id, product.toDto(userId = 1))
    }

    //DELETE
    override suspend fun deleteProduct(id: Int): GenericResponse {
        return apiService.deleteProduct(id)
    }
}