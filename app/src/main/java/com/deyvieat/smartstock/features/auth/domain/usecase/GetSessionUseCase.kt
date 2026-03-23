package com.deyvieat.smartstock.features.auth.domain.usecase

import com.deyvieat.smartstock.features.auth.domain.entities.UserSession
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): UserSession? = repository.getSession()
}