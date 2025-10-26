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
fun LoginScreen(onBack: () -> Unit, onGoRecuperar: () -> Unit) {
    val vm: AuthViewModel = viewModel()

    var correo by remember { mutableStateOf("") }
    var pass   by remember { mutableStateOf("") }
    var eCorreo by remember { mutableStateOf<String?>(null) }
    var ePass   by remember { mutableStateOf<String?>(null) }

    val duocRegex = remember { Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$") }

    Scaffold(topBar = { TopAppBar(title = { Text("Login") }) }) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                            if (eCorreo != null) eCorreo = null
                        },
                        label = { Text("Correo @duoc.cl") },
                        isError = eCorreo != null,
                        supportingText = { if (eCorreo != null) Text(eCorreo!!) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = pass,
                        onValueChange = {
                            pass = it
                            if (ePass != null) ePass = null
                        },
                        label = { Text("Contraseña") },
                        isError = ePass != null,
                        supportingText = { if (ePass != null) Text(ePass!!) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(onClick = onGoRecuperar, modifier = Modifier.weight(1f)) {
                            Text("Recuperar")
                        }
                        Button(
                            onClick = {
                                eCorreo = when {
                                    correo.isBlank() -> "Ingresa tu correo"
                                    !duocRegex.matches(correo.trim()) -> "Debe ser @duoc.cl"
                                    else -> null
                                }
                                ePass = if (pass.isBlank()) "Ingresa tu contraseña" else null

                                val ok = eCorreo == null && ePass == null
                                if (ok) {
                                    vm.clearError()
                                    vm.login(correo.trim(), pass)
                                    onBack() // vuelve a Home
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) { Text("Ingresar") }
                    }

                    OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
