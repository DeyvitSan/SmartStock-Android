package com.deyvieat.smartstock.features.inventory.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.deyvieat.smartstock.features.inventory.data.datasources.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    // Consulta compleja: filtrado por stock bajo (escenario Room del profe)
    @Query("SELECT * FROM products WHERE quantity <= :threshold ORDER BY quantity ASC")
    fun getLowStockProducts(threshold: Int = 5): Flow<List<ProductEntity>>

    @Upsert
    suspend fun upsertProducts(products: List<ProductEntity>)

    @Upsert
    suspend fun upsertProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query("SELECT * FROM products WHERE quantity <= :threshold")
    suspend fun getLowStockList(threshold: Int): List<ProductEntity>
}
