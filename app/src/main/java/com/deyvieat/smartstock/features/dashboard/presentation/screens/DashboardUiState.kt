package com.deyvieat.smartstock.features.dashboard.presentation.screens

import com.deyvieat.smartstock.features.inventory.domain.entities.Product

data class DashboardUiState(
    val totalProducts: Int = 0,
    val totalInventoryValue: Double = 0.0,
    val lowStockCount: Int = 0,
    val lowStockProducts: List<Product> = emptyList()
)
