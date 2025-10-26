package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appzonalibros.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(onBack: () -> Unit) {
    val vm: AuthViewModel = viewModel()
    val ui = vm.ui.collectAsState().value

    Scaffold(topBar = { TopAppBar(title = { Text("Mi Perfil") }) }) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Nombre: ${ui.nombre}")
            Text("Correo: ${ui.correo}")
            if (ui.loggedIn) {
                Button(onClick = { vm.logout(); onBack() }, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
            } else {
                Text("No has iniciado sesión")
            }
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
        }
    }
}
