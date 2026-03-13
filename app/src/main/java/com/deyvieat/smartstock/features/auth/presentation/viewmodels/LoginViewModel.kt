package com.deyvieat.smartstock.features.auth.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.core.network.LoginRequest
import com.deyvieat.smartstock.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onEmailChange(newValue: String) { email = newValue }
    fun onPasswordChange(newValue: String) { password = newValue }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                //UseCase y .trim() por seguridad
                val request = LoginRequest(email.trim(), password.trim())
                val response = loginUseCase(request)

                if (response.success) {
                    onSuccess()
                } else {
                    errorMessage = response.message ?: "Error desconocido"
                }

            } catch (e: HttpException) {
                if (e.code() == 401) {
                    errorMessage = "Usuario o contraseña incorrectos"
                } else {
                    errorMessage = "Error del servidor: ${e.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}