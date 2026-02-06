package com.deyvieat.smartstock.features.auth.domain.repository

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.core.network.LoginRequest
import com.deyvieat.smartstock.core.network.LoginResponse
import com.deyvieat.smartstock.core.network.RegisterRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest): GenericResponse
}