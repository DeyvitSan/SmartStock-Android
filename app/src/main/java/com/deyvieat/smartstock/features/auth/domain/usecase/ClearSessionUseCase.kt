package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.clearSession()
}
