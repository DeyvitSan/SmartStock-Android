package com.deyvieat.smartstock.features.inventory.data.datasources.remote.api

import com.deyvieat.smartstock.features.inventory.data.datasources.remote.models.GenericResponseDto
import com.deyvieat.smartstock.features.inventory.data.datasources.remote.models.ProductDto
import retrofit2.http.*

interface InventoryApi {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @POST("products")
    suspend fun addProduct(@Body product: ProductDto): GenericResponseDto

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: ProductDto): GenericResponseDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): GenericResponseDto
}
