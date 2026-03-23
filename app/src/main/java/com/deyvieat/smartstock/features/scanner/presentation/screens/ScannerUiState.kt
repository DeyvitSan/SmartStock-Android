package com.deyvieat.smartstock.features.scanner.presentation.screens

import com.deyvieat.smartstock.features.inventory.domain.entities.Product

data class ScannerUiState(
    val scannedCode: String = "",
    val foundProducts: List<Product> = emptyList(),
    val isTorchOn: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null
)
