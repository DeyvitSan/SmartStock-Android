package com.deyvieat.smartstock.features.auth.domain.entities

data class LoginResult(val success: Boolean, val message: String? = null)
data class RegisterResult(val success: Boolean, val message: String? = null)

data class UserSession(val email: String, val isLoggedIn: Boolean)
