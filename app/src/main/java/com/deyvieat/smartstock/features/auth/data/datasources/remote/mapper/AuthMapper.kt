package com.deyvieat.smartstock.features.auth.data.datasources.remote.mapper

import com.deyvieat.smartstock.features.auth.data.datasources.remote.models.LoginResponseDto
import com.deyvieat.smartstock.features.auth.domain.entities.LoginResult

fun LoginResponseDto.toDomain(): LoginResult = LoginResult(
    success = this.success,
    message = this.message
)
