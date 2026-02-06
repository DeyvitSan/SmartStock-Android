package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.core.network.GenericResponse
import com.deyvieat.smartstock.core.network.RegisterRequest
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: RegisterRequest): GenericResponse {
        return repository.register(request)
    }
}