package com.deyvieat.smartstock.features.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deyvieat.smartstock.features.inventory.domain.entities.Product

@Composable
fun MetricCard(modifier: Modifier = Modifier, title: String, value: String, containerColor: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = containerColor)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, style = MaterialTheme.typography.bodySmall)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LowStockCard(product: Product) {
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (product.quantity == 0) MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.surfaceVariant)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(product.name, fontWeight = FontWeight.SemiBold)
                Text("$${product.price}", style = MaterialTheme.typography.bodySmall)
            }
            Badge(containerColor = if (product.quantity == 0) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.tertiary) {
                Text("${product.quantity} uds")
            }
        }
    }
}
