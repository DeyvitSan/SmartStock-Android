package com.deyvieat.smartstock.features.auth.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deyvieat.smartstock.features.auth.presentation.viewmodels.RegisterViewModel

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val name by viewModel.name.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) onRegisterSuccess()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(value = name, onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))

        if (uiState.error != null) {
            Text(uiState.error!!, color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
        }

        Button(onClick = { viewModel.register() }, modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading) {
            if (uiState.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
            else Text("Registrarse")
        }
        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onBackToLogin) { Text("¿Ya tienes cuenta? Inicia Sesión") }
    }
}
