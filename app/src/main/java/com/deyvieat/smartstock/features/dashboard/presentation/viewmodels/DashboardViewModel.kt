package com.deyvieat.smartstock.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.core.hardware.VibrationManager
import com.deyvieat.smartstock.features.dashboard.domain.usecases.GetDashboardDataUseCase
import com.deyvieat.smartstock.features.dashboard.presentation.screens.DashboardUiState
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardData: GetDashboardDataUseCase,
    private val vibrationManager: VibrationManager   //inyectado desde core
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeMetrics()
        observeLowStock()
    }

    private fun observeMetrics() {
        viewModelScope.launch {
            getDashboardData.getAllProducts().collect { products ->
                _uiState.update {
                    it.copy(
                        totalProducts = products.size,
                        totalInventoryValue = products.sumOf { p: Product -> p.price * p.quantity }
                    )
                }
            }
        }
    }

    private fun observeLowStock() {
        viewModelScope.launch {
            getDashboardData.getLowStockProducts()
                .distinctUntilChanged()
                .collect { lowStock ->
                    if (lowStock.isNotEmpty()) {
                        vibrationManager.vibrateAlert()  // HARDWARE: patrón doble de alerta
                    }
                    _uiState.update {
                        it.copy(lowStockProducts = lowStock, lowStockCount = lowStock.size)
                    }
                }
        }
    }
}
