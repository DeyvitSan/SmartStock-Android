package com.deyvieat.smartstock.features.inventory.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deyvieat.smartstock.features.inventory.presentation.viewmodels.EditProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(viewModel: EditProductViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val price by viewModel.price.collectAsStateWithLifecycle()
    val quantity by viewModel.quantity.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) { if (uiState.isSuccess) onBack() }

    Scaffold(topBar = { TopAppBar(title = { Text("Editar Producto") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {

            OutlinedTextField(value = name, onValueChange = { viewModel.name.value = it },
                label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { viewModel.price.value = it },
                label = { Text("Precio") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = quantity, onValueChange = { viewModel.quantity.value = it },
                label = { Text("Cantidad") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth())

            if (uiState.error != null) Text(uiState.error!!, color = MaterialTheme.colorScheme.error)

            Button(onClick = { viewModel.updateProduct() }, modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading) { Text("Guardar Cambios") }

            OutlinedButton(onClick = { viewModel.deleteProduct() }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                enabled = !uiState.isLoading) { Text("Eliminar Producto") }
        }
    }
}
