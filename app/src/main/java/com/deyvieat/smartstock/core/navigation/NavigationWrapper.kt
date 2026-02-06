package com.deyvieat.smartstock.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationWrapper(
    navigationGraphs: List<FeatureNavGraph> // Recibe una lista (Auth, Inventory)
) {
    val navController = rememberNavController()

    // El punto de inicio es el Login
    NavHost(navController = navController, startDestination = Login) {

        // Aquí le decimos a cada feature que registre sus pantallas
        navigationGraphs.forEach { navGraph ->
            navGraph.registerGraph(this, navController)
        }
    }
}