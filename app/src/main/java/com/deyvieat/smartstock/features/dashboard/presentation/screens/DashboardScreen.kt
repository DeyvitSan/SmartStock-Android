package com.deyvieat.smartstock.features.dashboard.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deyvieat.smartstock.features.dashboard.presentation.components.LowStockCard
import com.deyvieat.smartstock.features.dashboard.presentation.components.MetricCard
import com.deyvieat.smartstock.features.dashboard.presentation.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onBack: () -> Unit, viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(title = { Text("Dashboard") },
            navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Atrás") } })
    }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard(modifier = Modifier.weight(1f), title = "Productos",
                        value = "${uiState.totalProducts}",
                        containerColor = MaterialTheme.colorScheme.primaryContainer)
                    MetricCard(modifier = Modifier.weight(1f), title = "Valor Total",
                        value = "$${"%.2f".format(uiState.totalInventoryValue)}",
                        containerColor = MaterialTheme.colorScheme.secondaryContainer)
                }
            }

            if (uiState.lowStockCount > 0) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.error)
                            Text(" ${uiState.lowStockCount} productos con stock bajo",
                                color = MaterialTheme.colorScheme.onErrorContainer, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item { Text("Stock Bajo (≤ 5 unidades)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) }

            if (uiState.lowStockProducts.isEmpty()) {
                item { Text(" Todo el inventario en niveles óptimos", color = MaterialTheme.colorScheme.primary) }
            } else {
                items(uiState.lowStockProducts) { product -> LowStockCard(product) }
            }
        }
    }
}
