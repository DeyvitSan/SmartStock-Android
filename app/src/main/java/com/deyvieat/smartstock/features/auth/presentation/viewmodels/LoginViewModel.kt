package com.deyvieat.smartstock.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.core.services.TokenApi
import com.deyvieat.smartstock.core.services.TokenDto
import com.deyvieat.smartstock.features.auth.domain.usecase.LoginUseCase
import com.deyvieat.smartstock.features.auth.presentation.screens.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.messaging.FirebaseMessaging

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenApi: TokenApi
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    var email = MutableStateFlow("")
    var password = MutableStateFlow("")

    fun onEmailChange(value: String) { email.value = value }
    fun onPasswordChange(value: String) { password.value = value }

    fun login() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = loginUseCase(email.value.trim(), password.value.trim())
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = {
                        FirebaseMessaging.getInstance().token.addOnSuccessListener { fcmToken ->
                            viewModelScope.launch {
                                try{
                                    tokenApi.saveToken(TokenDto(fcmToken))
                                }  catch (e: Exception) {
                                    //No critico si falla
                                }
                            }
                        }
                        currentState.copy(isLoading = false, isSuccess = true)
                    },
                    onFailure = { e -> currentState.copy(isLoading = false, error = e.message) }
                )
            }
        }
    }
}
