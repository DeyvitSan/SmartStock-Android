package com.deyvieat.smartstock.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.auth.domain.usecase.GetSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashDestination {
    object Loading : SplashDestination()
    object GoToLogin : SplashDestination()
    object GoToInventory : SplashDestination()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSession: GetSessionUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Loading)
    val destination = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            val session = getSession()
            _destination.value = if (session != null && session.isLoggedIn) {
                SplashDestination.GoToInventory
            } else {
                SplashDestination.GoToLogin
            }
        }
    }
}
