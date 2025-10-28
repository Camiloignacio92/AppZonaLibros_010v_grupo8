package com.example.appzonalibros.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appzonalibros.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    onBack: () -> Unit,
    onRegistered: () -> Unit // navegar a Perfil al terminar
) {
    val vm: AuthViewModel = viewModel()
    val ui = vm.ui.collectAsState().value

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var pass   by remember { mutableStateOf("") }
    var pass2  by remember { mutableStateOf("") }
    var phone  by remember { mutableStateOf("") }

    val generosLeft  = listOf("FICCIÓN", "MISTERIO", "FANTASÍA", "CIENCIA FICCIÓN", "ROMANCE")
    val generosRight = listOf("TERROR", "HISTÓRICO", "AVENTURA", "BIOGRAFÍA")
    var seleccion by remember { mutableStateOf(setOf<String>()) }

    // Errores locales (bajo campos)
    var eNombre by remember { mutableStateOf<String?>(null) }
    var eCorreo by remember { mutableStateOf<String?>(null) }
    var ePass   by remember { mutableStateOf<String?>(null) }
    var ePass2  by remember { mutableStateOf<String?>(null) }
    var ePhone  by remember { mutableStateOf<String?>(null) }
    var eGeneros by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("ZONALIBROS") }) },
        bottomBar = {
            Surface(tonalElevation = 3.dp) {
                Button(
                    onClick = {
                        // Validación visual rápida
                        eNombre = when {
                            nombre.isBlank() -> "Ingresa tu nombre"
                            nombre.length > 100 -> "Máximo 100 caracteres"
                            !nombre.all { it.isLetter() || it.isWhitespace() } -> "Solo letras y espacios"
                            else -> null
                        }
                        eCorreo = when {
                            correo.isBlank() -> "Ingresa tu correo"
                            correo.length > 60 -> "Máx 60"
                            !correo.endsWith("@duoc.cl", true) -> "Debe terminar en @duoc.cl"
                            else -> null
                        }
                        ePass = when {
                            pass.length < 10 -> "Mínimo 10"
                            !pass.any { it.isUpperCase() } -> "Falta mayúscula"
                            !pass.any { it.isLowerCase() } -> "Falta minúscula"
                            !pass.any { it.isDigit() } -> "Falta número"
                            !pass.any { "!@#\$%&*()_-+=[]{};:'\",.<>?/\\|`~^".contains(it) } -> "Falta símbolo"
                            else -> null
                        }
                        ePass2 = if (pass2 != pass) "No coincide" else null
                        ePhone = if (phone.isNotBlank() && phone.any { !it.isDigit() }) "Solo números" else null
                        eGeneros = if (seleccion.isEmpty()) "Selecciona al menos 1" else null

                        val ok = listOf(eNombre,eCorreo,ePass,ePass2,ePhone,eGeneros).all { it == null }
                        if (ok) {
                            vm.clearError()
                            vm.registrar(
                                nombre.trim(), correo.trim(), pass, pass2,
                                phone.trim(), seleccion.toList()
                            )
                            onRegistered() // navega a Perfil
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) { Text("REGISTRAR") }
            }
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Registro de usuario", style = MaterialTheme.typography.titleMedium)

            if (ui.error != null) Text(ui.error!!, color = MaterialTheme.colorScheme.error)

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; eNombre = null },
                label = { Text("NOMBRE COMPLETO") },
                isError = eNombre != null,
                supportingText = { eNombre?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it; eCorreo = null },
                label = { Text("CORREO (@duoc.cl)") },
                isError = eCorreo != null,
                supportingText = { eCorreo?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it; ePass = null },
                label = { Text("CONTRASEÑA (≥10, Aa1@)") },
                isError = ePass != null,
                supportingText = { ePass?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pass2,
                onValueChange = { pass2 = it; ePass2 = null },
                label = { Text("CONFIRMAR CONTRASEÑA") },
                isError = ePass2 != null,
                supportingText = { ePass2?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it; ePhone = null },
                label = { Text("TELÉFONO (opcional)") },
                isError = ePhone != null,
                supportingText = { ePhone?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Text("TIPO DE GÉNERO", style = MaterialTheme.typography.labelLarge)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    generosLeft.forEach { g ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = g in seleccion, onCheckedChange = {
                                seleccion = if (it) seleccion + g else seleccion - g
                                eGeneros = null
                            })
                            Text(g)
                        }
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                    generosRight.forEach { g ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = g in seleccion, onCheckedChange = {
                                seleccion = if (it) seleccion + g else seleccion - g
                                eGeneros = null
                            })
                            Text(g)
                        }
                    }
                }
            }
            eGeneros?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
