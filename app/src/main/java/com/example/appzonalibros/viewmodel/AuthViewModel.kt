package com.example.appzonalibros.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appzonalibros.data.UserPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AuthUiState(
    val nombre: String = "",
    val correo: String = "",
    val phone: String = "",
    val generos: List<String> = emptyList(),
    val loggedIn: Boolean = false,
    val error: String? = null
)

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val _ui = MutableStateFlow(AuthUiState())
    val ui: StateFlow<AuthUiState> = _ui.asStateFlow()

    init {
        // Cargar valores almacenados en DataStore
        viewModelScope.launch {
            combine(
                UserPrefs.nombre(getApplication()),
                UserPrefs.correo(getApplication()),
                UserPrefs.phone(getApplication()),
                UserPrefs.genres(getApplication()),
                UserPrefs.logged(getApplication())
            ) { n, c, p, g, l -> AuthUiState(n, c, p, g, l, null) }
                .collect { _ui.value = it }
        }
    }

    fun clearError() {
        _ui.value = _ui.value.copy(error = null)
    }

    // 🔹 Registrar usuario
    fun registrar(
        nombre: String,
        correo: String,
        pass: String,
        confirm: String,
        phone: String,
        generos: List<String>
    ) {
        val err = when {
            nombre.isBlank() || nombre.length > 100 || !nombre.all { it.isLetter() || it.isWhitespace() } ->
                "Nombre inválido (solo letras/espacios, máx 100)"
            correo.isBlank() || correo.length > 60 || !correo.endsWith("@duoc.cl", true) ->
                "Correo debe ser @duoc.cl (máx 60)"
            pass.length < 10 ||
                    !pass.any { it.isUpperCase() } ||
                    !pass.any { it.isLowerCase() } ||
                    !pass.any { it.isDigit() } ||
                    !pass.any { "!@#\$%&*()_-+=[]{};:'\",.<>?/\\|`~^".contains(it) } ->
                "Contraseña débil: min 10, mayús, minús, número y símbolo"
            pass != confirm -> "Las contraseñas no coinciden"
            phone.isNotBlank() && phone.any { !it.isDigit() } -> "Teléfono inválido (solo números)"
            generos.isEmpty() -> "Selecciona al menos un género"
            else -> null
        }

        if (err != null) {
            _ui.value = _ui.value.copy(error = err)
            return
        }

        viewModelScope.launch {
            UserPrefs.saveUser(getApplication(), nombre, correo, phone, generos)
        }
    }

    // 🔹 Iniciar sesión
    fun login(
        correo: String,
        pass: String,
        onResult: (ok: Boolean, error: String?) -> Unit = { _, _ -> }
    ) {
        val err = when {
            !correo.endsWith("@duoc.cl", ignoreCase = true) -> "Correo debe ser @duoc.cl"
            pass.isBlank() -> "Ingresa tu contraseña"
            else -> null
        }

        if (err != null) {
            _ui.value = _ui.value.copy(error = err)
            onResult(false, err)
            return
        }

        viewModelScope.launch {
            val nombreActual = if (_ui.value.nombre.isNotBlank()) _ui.value.nombre else "Usuario"
            val phoneActual = _ui.value.phone
            val generosAct = _ui.value.generos

            UserPrefs.saveUser(getApplication(), nombreActual, correo, phoneActual, generosAct)
            onResult(true, null)
        }
    }

    // 🔹 Cerrar sesión
    fun logout() {
        viewModelScope.launch {
            UserPrefs.clear(getApplication())
        }
    }
}

