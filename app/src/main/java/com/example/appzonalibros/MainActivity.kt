package com.example.appzonalibros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.appzonalibros.ui.theme.AppZonaLibrosTheme
import com.example.appzonalibros.navigation.AppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppZonaLibrosTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    com.example.appzonalibros.navigation.AppNavigation()
                }
            }
        }
    }
}
