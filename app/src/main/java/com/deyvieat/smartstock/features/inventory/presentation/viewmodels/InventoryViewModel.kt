package com.deyvieat.smartstock.features.inventory.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.launch

//Aquí recibe el usecase no la API
class InventoryViewModel(private val getProductsUseCase: GetProductsUseCase) : ViewModel() {

    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                //usamos el caso de uso
                products = getProductsUseCase()
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}