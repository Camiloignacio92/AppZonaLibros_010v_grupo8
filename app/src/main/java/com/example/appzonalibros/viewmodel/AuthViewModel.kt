package com.example.appzonalibros.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appzonalibros.data.UserPrefs
import com.example.appzonalibros.model.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AuthUiState(
    val nombre: String = "",
    val correo: String = "",
    val loggedIn: Boolean = false,
    val error: String? = null
)

class AuthViewModel(app: Application) : AndroidViewModel(app) {

    private val _ui = MutableStateFlow(AuthUiState())
    val ui: StateFlow<AuthUiState> = _ui.asStateFlow()

    init {
        // Cargar datos guardados
        viewModelScope.launch {
            combine(
                UserPrefs.nombre(getApplication()),
                UserPrefs.correo(getApplication()),
                UserPrefs.logged(getApplication())
            ) { n, c, l -> AuthUiState(nombre = n, correo = c, loggedIn = l) }
                .collect { _ui.value = it }
        }
    }

    fun registrar(nombre: String, correo: String, pass: String) {
        val err = validarRegistro(nombre, correo, pass)
        if (err != null) {
            _ui.update { it.copy(error = err) }
            return
        }
        viewModelScope.launch {
            UserPrefs.save(getApplication(), nombre, correo, logged = true)
        }
    }

    fun login(correo: String, pass: String) {
        val err = validarLogin(correo, pass)
        if (err != null) {
            _ui.update { it.copy(error = err) }
            return
        }
        viewModelScope.launch {
            // En un caso real verificarías contra backend; aquí damos acceso directo
            UserPrefs.save(getApplication(), _ui.value.nombre, correo, logged = true)
        }
    }

    fun logout() {
        viewModelScope.launch { UserPrefs.clear(getApplication()) }
    }

    fun clearError() { _ui.update { it.copy(error = null) } }

    // Validaciones mínimas
    private fun validarRegistro(nombre: String, correo: String, pass: String): String? {
        if (nombre.isBlank()) return "Ingresa tu nombre"
        if (!correo.endsWith("@duoc.cl")) return "El correo debe ser @duoc.cl"
        if (pass.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        return null
    }

    private fun validarLogin(correo: String, pass: String): String? {
        if (!correo.endsWith("@duoc.cl")) return "Correo debe ser @duoc.cl"
        if (pass.isBlank()) return "Ingresa tu contraseña"
        return null
    }
}
