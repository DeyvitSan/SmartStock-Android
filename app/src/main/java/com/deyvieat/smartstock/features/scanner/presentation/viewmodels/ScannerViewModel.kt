package com.deyvieat.smartstock.features.scanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.core.hardware.VibrationManager
import com.deyvieat.smartstock.features.scanner.domain.usecases.SearchProductByCodeUseCase
import com.deyvieat.smartstock.features.scanner.presentation.screens.ScannerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val searchProductByCode: SearchProductByCodeUseCase,
    private val vibrationManager: VibrationManager   //inyectado desde core
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState = _uiState.asStateFlow()

    fun onBarcodeDetected(code: String) {
        if (_uiState.value.isProcessing || code == _uiState.value.scannedCode) return

        _uiState.update { it.copy(scannedCode = code, isProcessing = true, error = null) }
        vibrationManager.vibrateShort()  // HARDWARE vibración corta al detectar QR

        viewModelScope.launch {
            val result = searchProductByCode(code)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { products ->
                        currentState.copy(isProcessing = false, foundProducts = products)
                    },
                    onFailure = { e ->
                        currentState.copy(isProcessing = false, foundProducts = emptyList(), error = e.message)
                    }
                )
            }
        }
    }

    fun toggleTorch() { _uiState.update { it.copy(isTorchOn = !it.isTorchOn) } }
    fun clearScan() { _uiState.update { ScannerUiState() } }
}
