package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            val result = repository.login(email, password)
            if (result.success) Result.success(Unit)
            else Result.failure(Exception(result.message ?: "Credenciales incorrectas"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}