package com.example.appzonalibros.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appzonalibros.ui.screens.HomeScreen
import com.example.appzonalibros.ui.screens.LoginScreen
import com.example.appzonalibros.ui.screens.PerfilScreen
import com.example.appzonalibros.ui.screens.RecuperarPassScreen
import com.example.appzonalibros.ui.screens.RegistroScreen

@Composable
fun AppNavigation() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Route.Home.path) {
        composable(Route.Home.path) {
            HomeScreen(
                onGoLogin = { nav.navigate(Route.Login.path) },
                onGoRegistro = { nav.navigate(Route.Registro.path) },
                onGoPerfil = { nav.navigate(Route.Perfil.path) }
            )
        }
        composable(Route.Login.path) {
            LoginScreen(
                onBack = { nav.popBackStack() },
                onGoRecuperar = { nav.navigate(Route.RecuperarPass.path) }
            )
        }
        composable(Route.Registro.path) { RegistroScreen(onBack = { nav.popBackStack() }) }
        composable(Route.Perfil.path)   { PerfilScreen(onBack = { nav.popBackStack() }) }
        composable(Route.RecuperarPass.path) { RecuperarPassScreen(onBack = { nav.popBackStack() }) }
    }
}

