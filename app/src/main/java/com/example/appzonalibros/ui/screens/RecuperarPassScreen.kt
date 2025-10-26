package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appzonalibros.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarPassScreen(onBack: () -> Unit) {
    val vm: AuthViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var correo by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recuperar contraseña") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo @duoc.cl") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val msg = if (correo.endsWith("@duoc.cl"))
                        "Se envió un enlace de recuperación a $correo"
                    else
                        "Correo inválido: debe ser @duoc.cl"

                    scope.launch { snackbarHostState.showSnackbar(message = msg) }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar correo")
            }

            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}
