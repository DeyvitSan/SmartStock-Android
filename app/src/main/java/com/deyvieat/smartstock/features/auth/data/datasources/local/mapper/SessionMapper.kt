package com.deyvieat.smartstock.features.auth.data.datasources.local.mapper

import com.deyvieat.smartstock.features.auth.data.datasources.local.entity.SessionEntity
import com.deyvieat.smartstock.features.auth.domain.entities.UserSession

fun SessionEntity.toDomain() = UserSession(email = email, isLoggedIn = isLoggedIn)

fun UserSession.toEntity() = SessionEntity(email = email, isLoggedIn = isLoggedIn)
