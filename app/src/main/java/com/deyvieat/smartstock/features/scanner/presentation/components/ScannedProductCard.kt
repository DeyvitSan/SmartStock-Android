package com.deyvieat.smartstock.features.scanner.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deyvieat.smartstock.features.inventory.domain.entities.Product

@Composable
fun ScannedProductCard(product: Product, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(product.name, fontWeight = FontWeight.Bold)
            Text("Precio: $${product.price}")
            Text("Stock: ${product.quantity} unidades",
                color = if (product.quantity <= 5) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface)
        }
    }
}
