package com.deyvieat.smartstock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.deyvieat.smartstock.core.navigation.NavigationWrapper
import com.deyvieat.smartstock.features.auth.navigation.AuthNavGraph
import com.deyvieat.smartstock.features.inventory.navigation.InventoryNavGraph
import com.deyvieat.smartstock.ui.theme.SmartStockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as SmartStockApplication).appContainer

        setContent {
            SmartStockTheme {
                val authGraph = AuthNavGraph(appContainer.authRepository)

                val inventoryGraph = InventoryNavGraph(appContainer.inventoryRepository)

                NavigationWrapper(
                    navigationGraphs = listOf(authGraph, inventoryGraph)
                )
            }
        }
    }
}