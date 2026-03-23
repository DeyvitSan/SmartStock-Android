package com.deyvieat.smartstock.features.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.usecases.GetProductsUseCase
import com.deyvieat.smartstock.features.inventory.presentation.screens.InventoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState = _uiState.asStateFlow()

    // Flow reactivo de Room: la UI se actualiza automáticamente ante cualquier cambio
    val products = getProductsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init { syncWithApi() }

    fun syncWithApi() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                getProductsUseCase.sync()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                // Sin conexión: Room sigue sirviendo datos del último sync
                _uiState.update { it.copy(isLoading = false, error = "Sin conexión - mostrando datos locales") }
            }
        }
    }
}
