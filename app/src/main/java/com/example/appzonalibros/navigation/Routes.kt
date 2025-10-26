package com.example.appzonalibros.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")
    data object Login : Route("login")
    data object Registro : Route("registro")
    data object Perfil : Route("perfil")
    data object RecuperarPass : Route("recuperar")
}

