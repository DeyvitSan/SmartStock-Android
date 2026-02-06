package com.deyvieat.smartstock.features.inventory.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.usecase.AddProductUseCase
import kotlinx.coroutines.launch

class AddProductViewModel(private val addProductUseCase: AddProductUseCase) : ViewModel() {

    // Estados del formulario
    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var quantity by mutableStateOf("")

    // Estados de la UI
    var isLoading by mutableStateOf(false)
    var statusMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false)

    fun onNameChange(v: String) { name = v }
    fun onPriceChange(v: String) { price = v }
    fun onQuantityChange(v: String) { quantity = v }

    fun saveProduct() {
        if (name.isBlank() || price.isBlank() || quantity.isBlank()) {
            statusMessage = "Llena todos los campos"
            return
        }

        val priceDouble = price.toDoubleOrNull()
        val qtyInt = quantity.toIntOrNull()

        if (priceDouble == null || priceDouble < 0) {
            statusMessage = "El precio debe ser válido y no puede ser negativo"
            return
        }

        if (qtyInt == null || qtyInt < 0) {
            statusMessage = "La cantidad debe ser válida y no puede ser negativa"
            return
        }

        viewModelScope.launch {
            isLoading = true
            statusMessage = null
            try {
                // Creamos la Entity (ID 0 porque es nuevo)
                val newProduct = Product(
                    id = 0,
                    name = name,
                    price = price.toDoubleOrNull() ?: 0.0,
                    quantity = quantity.toIntOrNull() ?: 0
                )

                val response = addProductUseCase(newProduct)

                if (response.success) {
                    statusMessage = "Producto guardado con éxito"
                    isSuccess = true
                } else {
                    statusMessage = "Error: ${response.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error al guardar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}