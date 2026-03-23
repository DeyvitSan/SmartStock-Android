package com.deyvieat.smartstock.features.inventory.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deyvieat.smartstock.features.inventory.presentation.viewmodels.AddProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(viewModel: AddProductViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val price by viewModel.price.collectAsStateWithLifecycle()
    val quantity by viewModel.quantity.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) { if (uiState.isSuccess) onBack() }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Nuevo Producto") },
            navigationIcon = { Button(onClick = onBack) { Text("<-") } })
    }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            OutlinedTextField(value = name, onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { viewModel.onPriceChange(it) },
                label = { Text("Precio") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = quantity, onValueChange = { viewModel.onQuantityChange(it) },
                label = { Text("Cantidad") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())

            if (uiState.error != null) Text(uiState.error!!, color = MaterialTheme.colorScheme.error)

            Button(onClick = { viewModel.saveProduct() }, modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading) {
                if (uiState.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else Text("Guardar")
            }
        }
    }
}
