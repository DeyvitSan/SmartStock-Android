package com.deyvieat.smartstock.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.core.network.RegisterRequest
import com.deyvieat.smartstock.features.auth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch
import android.util.Patterns

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    // Campos del formulario
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Estado de la pantalla
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    fun onNameChange(v: String) { name = v }
    fun onEmailChange(v: String) { email = v }
    fun onPasswordChange(v: String) { password = v }

    fun register() {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Todos los campos son obligatorios"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Ingresa un correo válido (ejemplo@correo.com)"
            return
        }

        if (password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val request = RegisterRequest(
                    name = name,
                    email = email.trim(),
                    password = password.trim()
                )

                val response = registerUseCase(request)

                if (response.success) {
                    isSuccess = true
                    errorMessage = null // Limpiamos errores
                } else {
                    errorMessage = response.message ?: "Error al registrar"
                }

            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}