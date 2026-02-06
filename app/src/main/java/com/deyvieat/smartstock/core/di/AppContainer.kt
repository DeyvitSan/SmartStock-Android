package com.deyvieat.smartstock.core.di

import android.content.Context
import com.deyvieat.smartstock.core.network.InventoryApiService
import com.deyvieat.smartstock.features.auth.data.repository.AuthRepositoryImpl
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import com.deyvieat.smartstock.features.inventory.data.repository.InventoryRepositoryImpl
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {

    private val baseUrl = "https://unenthralling-armandina-memorizable.ngrok-free.dev/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Esta es la API Única que usaremos en toda la app
    val inventoryApiService: InventoryApiService by lazy {
        retrofit.create(InventoryApiService::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(inventoryApiService)
    }

    val inventoryRepository: InventoryRepository by lazy {
        InventoryRepositoryImpl(inventoryApiService)
    }

}
