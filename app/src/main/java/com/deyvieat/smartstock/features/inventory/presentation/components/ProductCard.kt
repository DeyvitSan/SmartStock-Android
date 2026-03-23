package com.deyvieat.smartstock.features.inventory.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deyvieat.smartstock.features.inventory.domain.entities.Product

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(elevation = CardDefaults.cardElevation(4.dp), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Text(product.name, style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Cantidad: ${product.quantity}",
                    color = if (product.quantity <= 5) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface)
                Text("$${product.price}", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}
