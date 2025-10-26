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
fun RegistroScreen(onBack: () -> Unit) {
    val vm: AuthViewModel = viewModel()

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var pass   by remember { mutableStateOf("") }

    // Errores por campo
    var eNombre by remember { mutableStateOf<String?>(null) }
    var eCorreo by remember { mutableStateOf<String?>(null) }
    var ePass   by remember { mutableStateOf<String?>(null) }

    val duocRegex = remember { Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$") }

    Scaffold(topBar = { TopAppBar(title = { Text("Registro") }) }) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            if (eNombre != null) eNombre = null
                        },
                        label = { Text("Nombre completo") },
                        isError = eNombre != null,
                        supportingText = { if (eNombre != null) Text(eNombre!!) },
                        modifier = Modifier.fillMaxWidth()
                    )

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
                        label = { Text("Contraseña (min 6, 1 número)") },
                        isError = ePass != null,
                        supportingText = { if (ePass != null) Text(ePass!!) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            // Validaciones locales
                            eNombre = if (nombre.isBlank()) "Ingresa tu nombre" else null
                            eCorreo = when {
                                correo.isBlank() -> "Ingresa tu correo"
                                !duocRegex.matches(correo.trim()) -> "Debe ser un correo @duoc.cl válido"
                                else -> null
                            }
                            ePass = when {
                                pass.length < 6 -> "Mínimo 6 caracteres"
                                !pass.any { it.isDigit() } -> "Debe contener al menos 1 número"
                                else -> null
                            }

                            val ok = eNombre == null && eCorreo == null && ePass == null
                            if (ok) {
                                vm.clearError()
                                vm.registrar(nombre.trim(), correo.trim(), pass)
                                onBack()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Crear cuenta") }

                    OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
