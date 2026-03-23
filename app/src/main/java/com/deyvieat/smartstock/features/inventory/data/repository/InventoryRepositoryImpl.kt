package com.deyvieat.smartstock.features.inventory.data.repository

import com.deyvieat.smartstock.features.inventory.data.datasources.local.dao.ProductDao
import com.deyvieat.smartstock.features.inventory.data.datasources.local.mapper.toDomain
import com.deyvieat.smartstock.features.inventory.data.datasources.local.mapper.toEntity
import com.deyvieat.smartstock.features.inventory.data.datasources.remote.api.InventoryApi
import com.deyvieat.smartstock.features.inventory.data.datasources.remote.mapper.toDomain
import com.deyvieat.smartstock.features.inventory.data.datasources.remote.mapper.toDto
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val api: InventoryApi,
    private val dao: ProductDao
) : InventoryRepository {

    override fun getProductsStream(): Flow<List<Product>> =
        dao.getAllProducts().map { it.map { entity -> entity.toDomain() } }

    override fun getLowStockStream(threshold: Int): Flow<List<Product>> =
        dao.getLowStockProducts(threshold).map { it.map { entity -> entity.toDomain() } }

    override suspend fun syncProducts() {
        val remoteProducts = api.getProducts()
        dao.clearAll()
        dao.upsertProducts(remoteProducts.map { it.toDomain().toEntity() })
    }

    override suspend fun addProduct(product: Product): Result<Unit> {
        return try {
            val response = api.addProduct(product.toDto())
            if (response.success) {
                //sincroniza desde API para obtener el ID real asignado por mySQL
                syncProducts()
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "Error al agregar"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun updateProduct(product: Product): Result<Unit> {
        return try {
            val response = api.updateProduct(product.id, product.toDto())
            if (response.success) {
                dao.upsertProduct(product.toEntity())
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "Error al actualizar"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }

    override suspend fun deleteProduct(id: Int): Result<Unit> {
        return try {
            val response = api.deleteProduct(id)
            if (response.success) {
                dao.deleteProduct(id)
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message ?: "Error al eliminar"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }
}
