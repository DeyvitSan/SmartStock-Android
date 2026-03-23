package com.deyvieat.smartstock.features.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.domain.usecases.AddProductUseCase
import com.deyvieat.smartstock.features.inventory.presentation.screens.AddProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState = _uiState.asStateFlow()

    var name = MutableStateFlow("")
    var price = MutableStateFlow("")
    var quantity = MutableStateFlow("")

    fun onNameChange(v: String) { name.value = v }
    fun onPriceChange(v: String) { price.value = v }
    fun onQuantityChange(v: String) { quantity.value = v }

    fun saveProduct() {
        val priceDouble = price.value.toDoubleOrNull()
        val qtyInt = quantity.value.toIntOrNull()

        if (name.value.isBlank() || priceDouble == null || qtyInt == null || priceDouble < 0 || qtyInt < 0) {
            _uiState.update { it.copy(error = "Verifica que todos los campos sean válidos") }; return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = addProductUseCase(Product(0, name.value, priceDouble, qtyInt))
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { currentState.copy(isLoading = false, isSuccess = true) },
                    onFailure = { e -> currentState.copy(isLoading = false, error = e.message) }
                )
            }
        }
    }
}
