package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarPassScreen(onBack: () -> Unit) {
    var correo by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Recuperar contraseña") }) }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(correo, { correo = it }, label = { Text("Correo @duoc.cl") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
        }
    }
}
