package com.deyvieat.smartstock.features.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.usecases.GetProductsUseCase
import com.deyvieat.smartstock.features.inventory.presentation.screens.InventoryUiState
import com.deyvieat.smartstock.features.notifications.domain.repository.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val notificationsRepository: NotificationsRepository  // 👈
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState = _uiState.asStateFlow()

    val products = getProductsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        syncWithApi()
        // Observa cambios en productos y genera alertas de stock bajo
        products.onEach { list ->
            list.filter { it.quantity <= 5 }.forEach { product ->
                notificationsRepository.addNotification(product.name, product.quantity)
            }
        }.launchIn(viewModelScope)
    }

    fun syncWithApi() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                getProductsUseCase.sync()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Sin conexión - mostrando datos locales") }
            }
        }
    }
}