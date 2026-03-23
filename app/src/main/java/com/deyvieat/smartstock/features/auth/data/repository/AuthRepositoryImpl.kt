package com.deyvieat.smartstock.features.auth.data.repository

import com.deyvieat.smartstock.features.auth.data.datasources.local.dao.SessionDao
import com.deyvieat.smartstock.features.auth.data.datasources.local.entity.SessionEntity
import com.deyvieat.smartstock.features.auth.data.datasources.local.mapper.toDomain
import com.deyvieat.smartstock.features.auth.data.datasources.remote.api.AuthApi
import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.LoginRequestDto
import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.RegisterRequestDto
import com.deyvieat.smartstock.features.auth.domain.entities.LoginResult
import com.deyvieat.smartstock.features.auth.domain.entities.RegisterResult
import com.deyvieat.smartstock.features.auth.domain.entities.UserSession
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionDao: SessionDao
) : AuthRepository {

    override suspend fun login(email: String, password: String): LoginResult {
        return try {
            val response = api.login(LoginRequestDto(email, password))
            if (response.success) {
                // Guardar sesión en Room para acceso offline futuro
                sessionDao.saveSession(SessionEntity(email = email, isLoggedIn = true))
            }
            LoginResult(success = response.success, message = response.message)
        } catch (e: Exception) {
            // Sin internet: verificar si hay sesión guardada en Room
            val session = sessionDao.getSession()
            if (session != null && session.isLoggedIn) {
                LoginResult(success = true, message = "Sesión local restaurada")
            } else {
                LoginResult(success = false, message = "Sin conexión y no hay sesión guardada")
            }
        }
    }

    override suspend fun register(name: String, email: String, password: String): RegisterResult {
        val response = api.register(RegisterRequestDto(name, email, password))
        return RegisterResult(success = response.success, message = response.message)
    }

    override suspend fun getSession(): UserSession? =
        sessionDao.getSession()?.toDomain()

    override suspend fun saveSession(email: String) =
        sessionDao.saveSession(SessionEntity(email = email, isLoggedIn = true))

    override suspend fun clearSession() =
        sessionDao.clearSession()
}
