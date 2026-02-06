package com.deyvieat.smartstock.core.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InventoryApiService {

    //AUTH
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): GenericResponse

    //INVENTORY CRUD COMPLETO

    // 1. LEER (Read)
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    // 2. CREAR (Create)
    @POST("products")
    suspend fun addProduct(@Body product: ProductDto): GenericResponse

    // 3. ACTUALIZAR (Update)
    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: ProductDto
    ): GenericResponse

    // 4. ELIMINAR (Delete)
    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): GenericResponse
}