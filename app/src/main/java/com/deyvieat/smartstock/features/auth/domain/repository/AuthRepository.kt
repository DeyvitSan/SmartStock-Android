package com.deyvieat.smartstock.features.auth.domain.repository

import com.deyvieat.smartstock.features.auth.domain.entities.LoginResult
import com.deyvieat.smartstock.features.auth.domain.entities.RegisterResult
import com.deyvieat.smartstock.features.auth.domain.entities.UserSession

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginResult
    suspend fun register(name: String, email: String, password: String): RegisterResult

    // Sesión local Room permite login offline
    suspend fun getSession(): UserSession?
    suspend fun saveSession(email: String)
    suspend fun clearSession()
}