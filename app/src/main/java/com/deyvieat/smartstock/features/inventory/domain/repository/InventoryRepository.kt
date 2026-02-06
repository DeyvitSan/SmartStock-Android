package com.deyvieat.smartstock.features.inventory.domain.repository

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.features.inventory.domain.entity.Product

interface InventoryRepository {
    suspend fun getProducts(): List<Product>
    suspend fun addProduct(product: Product): GenericResponse

    suspend fun updateProduct(product: Product): GenericResponse

    suspend fun deleteProduct(id: Int): GenericResponse
}