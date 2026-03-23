package com.deyvieat.smartstock.features.auth.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.auth.domain.usecase.RegisterUseCase
import com.deyvieat.smartstock.features.auth.presentation.screens.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    var name = MutableStateFlow("")
    var email = MutableStateFlow("")
    var password = MutableStateFlow("")

    fun onNameChange(v: String) { name.value = v }
    fun onEmailChange(v: String) { email.value = v }
    fun onPasswordChange(v: String) { password.value = v }

    fun register() {
        if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank()) {
            _uiState.update { it.copy(error = "Todos los campos son obligatorios") }; return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            _uiState.update { it.copy(error = "Correo inválido") }; return
        }
        if (password.value.length < 6) {
            _uiState.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }; return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = registerUseCase(name.value, email.value.trim(), password.value.trim())
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { currentState.copy(isLoading = false, isSuccess = true) },
                    onFailure = { e -> currentState.copy(isLoading = false, error = e.message) }
                )
            }
        }
    }
}
