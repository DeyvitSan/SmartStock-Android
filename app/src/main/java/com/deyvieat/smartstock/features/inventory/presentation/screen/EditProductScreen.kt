package com.deyvieat.smartstock.features.inventory.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deyvieat.smartstock.features.inventory.presentation.viewmodel.EditProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    viewModel: EditProductViewModel,
    onBack: () -> Unit
) {
    if (viewModel.isSuccess) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(800)
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Producto") })
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.price,
                onValueChange = { viewModel.price = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.quantity,
                onValueChange = { viewModel.quantity = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.statusMessage != null) {
                Text(text = viewModel.statusMessage!!, color = Color.Magenta)
            }

            // BOTÓN ACTUALIZAR
            Button(
                onClick = { viewModel.updateProduct() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isLoading
            ) {
                Text("Guardar Cambios")
            }

            // BOTÓN ELIMINAR
            OutlinedButton(
                onClick = { viewModel.deleteProduct() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                enabled = !viewModel.isLoading
            ) {
                Text("Eliminar Producto")
            }
        }
    }
}