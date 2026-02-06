package com.deyvieat.smartstock.features.auth.data.repository

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.core.network.InventoryApiService
import com.deyvieat.smartstock.core.network.LoginRequest
import com.deyvieat.smartstock.core.network.LoginResponse
import com.deyvieat.smartstock.core.network.RegisterRequest
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val apiService: InventoryApiService) : AuthRepository {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    override suspend fun register(request: RegisterRequest): GenericResponse {
        return apiService.register(request)
    }
}