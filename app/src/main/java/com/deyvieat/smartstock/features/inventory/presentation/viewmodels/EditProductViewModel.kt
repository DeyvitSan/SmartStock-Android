package com.deyvieat.smartstock.features.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.usecases.DeleteProductUseCase
import com.deyvieat.smartstock.features.inventory.domain.usecases.UpdateProductUseCase
import com.deyvieat.smartstock.features.inventory.presentation.screens.EditProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProductViewModel @Inject constructor(
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProductUiState())
    val uiState = _uiState.asStateFlow()

    var id = MutableStateFlow(0)
    var name = MutableStateFlow("")
    var price = MutableStateFlow("")
    var quantity = MutableStateFlow("")

    fun loadProduct(product: Product) {
        id.value = product.id; name.value = product.name
        price.value = product.price.toString(); quantity.value = product.quantity.toString()
    }

    fun updateProduct() {
        val priceDouble = price.value.toDoubleOrNull()
        val qtyInt = quantity.value.toIntOrNull()
        if (priceDouble == null || qtyInt == null) {
            _uiState.update { it.copy(error = "Datos inválidos") }; return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = updateProductUseCase(Product(id.value, name.value, priceDouble, qtyInt))
            _uiState.update { s -> result.fold(
                onSuccess = { s.copy(isLoading = false, isSuccess = true) },
                onFailure = { e -> s.copy(isLoading = false, error = e.message) }
            )}
        }
    }

    fun deleteProduct() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = deleteProductUseCase(id.value)
            _uiState.update { s -> result.fold(
                onSuccess = { s.copy(isLoading = false, isSuccess = true) },
                onFailure = { e -> s.copy(isLoading = false, error = e.message) }
            )}
        }
    }
}
