package com.deyvieat.smartstock.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deyvieat.smartstock.features.auth.presentation.screens.LoginScreen
import com.deyvieat.smartstock.features.auth.presentation.screens.RegisterScreen
import com.deyvieat.smartstock.features.auth.presentation.viewmodels.LoginViewModel
import com.deyvieat.smartstock.features.auth.presentation.viewmodels.RegisterViewModel
import com.deyvieat.smartstock.features.dashboard.presentation.screens.DashboardScreen
import com.deyvieat.smartstock.features.inventory.domain.entities.Product
import com.deyvieat.smartstock.features.inventory.presentation.screens.AddProductScreen
import com.deyvieat.smartstock.features.inventory.presentation.screens.EditProductScreen
import com.deyvieat.smartstock.features.inventory.presentation.screens.InventoryHomeScreen
import com.deyvieat.smartstock.features.inventory.presentation.viewmodels.AddProductViewModel
import com.deyvieat.smartstock.features.inventory.presentation.viewmodels.EditProductViewModel
import com.deyvieat.smartstock.features.inventory.presentation.viewmodels.InventoryViewModel
import com.deyvieat.smartstock.features.scanner.presentation.screens.ScannerScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Splash) {

        // ── Splash: decide el destino inicial según sesión guardada en Room ──
        composable<Splash> {
            val viewModel: SplashViewModel = hiltViewModel()
            val destination by viewModel.destination.collectAsStateWithLifecycle()

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

            LaunchedEffect(destination) {
                when (destination) {
                    is SplashDestination.GoToInventory -> navController.navigate(InventoryHome) {
                        popUpTo(Splash) { inclusive = true }
                    }
                    is SplashDestination.GoToLogin -> navController.navigate(Login) {
                        popUpTo(Splash) { inclusive = true }
                    }
                    else -> Unit
                }
            }
        }

        // ── Login ─────────────────────────────────────────────────────────────
        composable<Login> {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(InventoryHome) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Register) }
            )
        }

        // ── Register ──────────────────────────────────────────────────────────
        composable<Register> {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = { navController.popBackStack() },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // ── Inventario ────────────────────────────────────────────────────────
        composable<InventoryHome> {
            val viewModel: InventoryViewModel = hiltViewModel()
            InventoryHomeScreen(
                viewModel = viewModel,
                onAddProductClick = { navController.navigate(AddProduct) },
                onProductClick = { product ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle?.set("product_data", product)
                    navController.navigate(EditProduct(product.id))
                },
                onScannerClick = { navController.navigate(Scanner) },
                onDashboardClick = { navController.navigate(Dashboard) }
            )
        }

        composable<AddProduct> {
            val viewModel: AddProductViewModel = hiltViewModel()
            AddProductScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        // Fix: previousBackStackEntry para leer el producto correctamente
        composable<EditProduct> {
            val viewModel: EditProductViewModel = hiltViewModel()
            val product = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Product>("product_data")
            LaunchedEffect(product) { product?.let { viewModel.loadProduct(it) } }
            EditProductScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        // ── Scanner F02 ───────────────────────────────────────────────────────
        composable<Scanner> {
            ScannerScreen(
                onBack = { navController.popBackStack() },
                onProductClick = { product ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle?.set("product_data", product)
                    navController.navigate(EditProduct(product.id))
                }
            )
        }

        // ── Dashboard F03 ─────────────────────────────────────────────────────
        composable<Dashboard> {
            DashboardScreen(onBack = { navController.popBackStack() })
        }

        composable<Notifications> {
            NotificationsScreen(onBack = { navController.popBackStack()})
        }
    }
}
