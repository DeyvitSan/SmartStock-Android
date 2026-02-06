package com.deyvieat.smartstock.features.auth.navigation


import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.deyvieat.smartstock.core.navigation.FeatureNavGraph
import com.deyvieat.smartstock.core.navigation.Login
import com.deyvieat.smartstock.core.navigation.Register
import com.deyvieat.smartstock.core.navigation.InventoryHome
import com.deyvieat.smartstock.features.auth.domain.repository.AuthRepository
import com.deyvieat.smartstock.features.auth.domain.usecase.RegisterUseCase
import com.deyvieat.smartstock.features.auth.domain.usecase.LoginUseCase
import com.deyvieat.smartstock.features.auth.presentation.screen.LoginScreen
import com.deyvieat.smartstock.features.auth.presentation.screen.RegisterScreen
import com.deyvieat.smartstock.features.auth.presentation.viewmodel.LoginViewModel
import com.deyvieat.smartstock.features.auth.presentation.viewmodel.RegisterViewModel

class AuthNavGraph(private val authRepository: AuthRepository) : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        // RUTA LOGIN
        navGraphBuilder.composable<Login> {
            // Inyección de Dependencias Manual (Clean Architecture)
            val viewModel: LoginViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        val useCase = LoginUseCase(authRepository)
                        LoginViewModel(useCase)
                    }
                }
            )

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    // Al entrar, vamos al Inventario y borramos el Login del historial
                    navController.navigate(InventoryHome) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Register)
                }
            )
        }

        // RUTA REGISTER
        navGraphBuilder.composable<Register> {
            val viewModel: RegisterViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        val useCase = RegisterUseCase(authRepository)
                        RegisterViewModel(useCase)
                    }
                }
            )

            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}