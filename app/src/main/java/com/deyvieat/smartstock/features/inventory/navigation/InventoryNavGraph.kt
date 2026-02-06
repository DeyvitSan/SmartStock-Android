package com.deyvieat.smartstock.features.inventory.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.deyvieat.smartstock.core.navigation.AddProduct
import com.deyvieat.smartstock.core.navigation.FeatureNavGraph
import com.deyvieat.smartstock.core.navigation.InventoryHome
import com.deyvieat.smartstock.core.navigation.EditProduct
import com.deyvieat.smartstock.features.inventory.domain.entity.Product
import com.deyvieat.smartstock.features.inventory.domain.repository.InventoryRepository
import com.deyvieat.smartstock.features.inventory.domain.usecase.AddProductUseCase
import com.deyvieat.smartstock.features.inventory.domain.usecase.GetProductsUseCase
import com.deyvieat.smartstock.features.inventory.domain.usecase.DeleteProductUseCase
import com.deyvieat.smartstock.features.inventory.domain.usecase.UpdateProductUseCase
import com.deyvieat.smartstock.features.inventory.presentation.screen.AddProductScreen
import com.deyvieat.smartstock.features.inventory.presentation.screen.InventoryHomeScreen
import com.deyvieat.smartstock.features.inventory.presentation.screen.EditProductScreen
import com.deyvieat.smartstock.features.inventory.presentation.viewmodel.AddProductViewModel
import com.deyvieat.smartstock.features.inventory.presentation.viewmodel.InventoryViewModel
import com.deyvieat.smartstock.features.inventory.presentation.viewmodel.EditProductViewModel

class InventoryNavGraph(private val inventoryRepository: InventoryRepository) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // PANTALLA 1: LISTA (HOME)
        navGraphBuilder.composable<InventoryHome> {
            val viewModel: InventoryViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        val useCase = GetProductsUseCase(inventoryRepository)
                        InventoryViewModel(useCase)
                    }
                }
            )

            androidx.compose.runtime.LaunchedEffect(Unit) {
                viewModel.fetchProducts()
            }

            InventoryHomeScreen(
                viewModel = viewModel,
                onAddProductClick = { navController.navigate(AddProduct)},
                onProductClick = { product ->
                    navController.navigate(EditProduct(product.id))
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "product_data",
                        product
                    )
                }
            )
        }

        // PANTALLA 2: AGREGAR
        navGraphBuilder.composable<AddProduct> {
            val viewModel: AddProductViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        val useCase = AddProductUseCase(inventoryRepository)
                        AddProductViewModel(useCase)
                    }
                }
            )
            AddProductScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        navGraphBuilder.composable<EditProduct> {
            val viewModel: EditProductViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        EditProductViewModel(
                            UpdateProductUseCase(inventoryRepository),
                            DeleteProductUseCase(inventoryRepository)
                        )
                    }
                }
            )
            val product =
                navController.currentBackStackEntry?.savedStateHandle?.get<Product>("product_data")

            androidx.compose.runtime.LaunchedEffect(product) {
                product?.let { viewModel.loadProduct(it) }
            }

            EditProductScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}