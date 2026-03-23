package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.features.auth.domain.entities.RegisterResult
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<RegisterResult> {
        return try {
            val result = repository.register(name, email, password)
            if (result.success) Result.success(result)
            else Result.failure(Exception(result.message ?: "Error al registrar"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}