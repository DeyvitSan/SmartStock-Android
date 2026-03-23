package com.deyvieat.smartstock.features.auth.presentation.screens

data class RegisterUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)
