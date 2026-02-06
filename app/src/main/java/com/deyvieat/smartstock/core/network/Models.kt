package com.deyvieat.smartstock.core.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse(
    val success: Boolean,
    val message: String? = null
)

//AUTH DTOs
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String? = null,
    val user: UserDto? = null
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)

//INVENTORY DTOs
@Serializable
data class ProductDto(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    @SerialName("user_id") val userId: Int
)