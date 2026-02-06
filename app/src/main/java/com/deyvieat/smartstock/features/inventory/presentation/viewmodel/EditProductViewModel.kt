package com.deyvieat.smartstock.features.inventory.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.usecase.DeleteProductUseCase
import com.deyvieat.smartstock.features.inventory.domain.usecase.UpdateProductUseCase
import kotlinx.coroutines.launch

class EditProductViewModel(
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    // Datos del formulario
    var id by mutableStateOf(0)
    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var quantity by mutableStateOf("")

    // Estado UI
    var isLoading by mutableStateOf(false)
    var statusMessage by mutableStateOf<String?>(null)
    var isSuccess by mutableStateOf(false) // Sirve para cerrar la pantalla

    // Cargar datos iniciales (cuando entras a editar)
    fun loadProduct(product: Product) {
        id = product.id
        name = product.name
        price = product.price.toString()
        quantity = product.quantity.toString()
    }

    fun updateProduct() {
        // Validaciones
        val priceDouble = price.toDoubleOrNull()
        val qtyInt = quantity.toIntOrNull()

        if (priceDouble == null || priceDouble < 0 || qtyInt == null || qtyInt < 0) {
            statusMessage = "Datos inválidos"
            return
        }

        viewModelScope.launch {
            isLoading = true
            statusMessage = null
            try {
                val updatedProduct = Product(id, name, priceDouble, qtyInt)
                val response = updateProductUseCase(updatedProduct)

                if (response.success) {
                    statusMessage = "Actualizado correctamente"
                    isSuccess = true
                } else {
                    statusMessage = "Error: ${response.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteProduct() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = deleteProductUseCase(id)
                if (response.success) {
                    statusMessage = "Producto eliminado"
                    isSuccess = true
                } else {
                    statusMessage = "Error al eliminar: ${response.message}"
                }
            } catch (e: Exception) {
                statusMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}