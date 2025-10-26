package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appzonalibros.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoLogin: () -> Unit,
    onGoRegistro: () -> Unit,
    onGoPerfil: () -> Unit
) {
    val vm: AuthViewModel = viewModel()
    val ui = vm.ui.collectAsState().value

    Scaffold(
        topBar = { TopAppBar(title = { Text("ZonaLibros") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (ui.loggedIn) {
                Text(
                    text = if (ui.nombre.isNotBlank()) "¡Hola, ${ui.nombre}!" else "¡Hola!",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = ui.correo.ifBlank { "Sesión iniciada" })

                Button(onClick = onGoPerfil, modifier = Modifier.fillMaxWidth()) {
                    Text("Ir a mi perfil")
                }
                OutlinedButton(
                    onClick = { vm.logout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar sesión")
                }
            } else {
                Text("Bienvenido a ZonaLibros", style = MaterialTheme.typography.titleLarge)
                Text("Inicia sesión o crea tu cuenta para continuar.")

                Button(onClick = onGoLogin, modifier = Modifier.fillMaxWidth()) {
                    Text("Ir a Login")
                }
                OutlinedButton(onClick = onGoRegistro, modifier = Modifier.fillMaxWidth()) {
                    Text("Crear cuenta")
                }
            }
        }
    }
}
