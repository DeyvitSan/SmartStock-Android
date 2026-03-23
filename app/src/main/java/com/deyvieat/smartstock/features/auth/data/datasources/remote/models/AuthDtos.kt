package com.deyvieat.smartstock.features.auth.data.datasources.remote.models

data class LoginRequestDto(val email: String, val password: String)

data class LoginResponseDto(
    val success: Boolean,
    val message: String? = null,
    val user: UserDto? = null
)

data class UserDto(val id: Int, val name: String, val email: String)

data class RegisterRequestDto(val name: String, val email: String, val password: String)

data class GenericResponseDto(val success: Boolean, val message: String? = null)
