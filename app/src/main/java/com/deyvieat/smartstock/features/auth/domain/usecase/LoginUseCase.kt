package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.core.network.LoginRequest
import com.deyvieat.smartstock.core.network.LoginResponse
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: LoginRequest): LoginResponse {
        return repository.login(request)
    }
}