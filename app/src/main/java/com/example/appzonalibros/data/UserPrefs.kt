package com.example.appzonalibros.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(DS_NAME)

object UserPrefs {
    private val KEY_NOMBRE = stringPreferencesKey("nombre")
    private val KEY_CORREO = stringPreferencesKey("correo")
    private val KEY_LOGGED = booleanPreferencesKey("logged")

    fun nombre(ctx: Context): Flow<String> =
        ctx.dataStore.data.map { it[KEY_NOMBRE] ?: "" }

    fun correo(ctx: Context): Flow<String> =
        ctx.dataStore.data.map { it[KEY_CORREO] ?: "" }

    fun logged(ctx: Context): Flow<Boolean> =
        ctx.dataStore.data.map { it[KEY_LOGGED] ?: false }

    suspend fun save(ctx: Context, nombre: String, correo: String, logged: Boolean) {
        ctx.dataStore.edit {
            it[KEY_NOMBRE] = nombre
            it[KEY_CORREO] = correo
            it[KEY_LOGGED] = logged
        }
    }

    suspend fun clear(ctx: Context) {
        ctx.dataStore.edit {
            it.remove(KEY_NOMBRE)
            it.remove(KEY_CORREO)
            it[KEY_LOGGED] = false
        }
    }
}


