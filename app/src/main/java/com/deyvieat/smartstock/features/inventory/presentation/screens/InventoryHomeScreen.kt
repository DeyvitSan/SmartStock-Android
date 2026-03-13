package com.deyvieat.smartstock.features.inventory.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.presentation.viewmodel.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHomeScreen(
    viewModel: InventoryViewModel,
    onAddProductClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Mi Inventario") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProductClick) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {

            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
            } else if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage!!,
                    color = Color.Red,
                    modifier = Modifier.fillMaxSize().wrapContentSize()
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(viewModel.products) { product ->
                        ProductItem(product, onClick = { onProductClick(product)})
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Cantidad: ${product.quantity}")
                Text(text = "$${product.price}", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}