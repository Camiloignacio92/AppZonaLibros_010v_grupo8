package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoLogin: () -> Unit,
    onGoRegistro: () -> Unit,
    onGoPerfil: () -> Unit
) {
    Scaffold(topBar = { TopAppBar(title = { Text("ZonaLibros") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenido a ZonaLibros", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onGoLogin, modifier = Modifier.fillMaxWidth()) { Text("Ir a Login") }
            Button(onClick = onGoRegistro, modifier = Modifier.fillMaxWidth()) { Text("Ir a Registro") }
            Button(onClick = onGoPerfil, modifier = Modifier.fillMaxWidth()) { Text("Ir a Perfil") }
        }
    }
}
