package com.example.appzonalibros.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DS_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(name = DS_NAME)

object UserPrefs {
    private val KEY_NOMBRE = stringPreferencesKey("nombre")
    private val KEY_CORREO = stringPreferencesKey("correo")
    private val KEY_LOGGED = booleanPreferencesKey("logged")
    private val KEY_PHONE  = stringPreferencesKey("phone")
    private val KEY_GENRES = stringPreferencesKey("genres_csv")

    fun nombre(ctx: Context): Flow<String> = ctx.dataStore.data.map { it[KEY_NOMBRE] ?: "" }
    fun correo(ctx: Context): Flow<String> = ctx.dataStore.data.map { it[KEY_CORREO] ?: "" }
    fun logged(ctx: Context): Flow<Boolean> = ctx.dataStore.data.map { it[KEY_LOGGED] ?: false }
    fun phone(ctx: Context): Flow<String> = ctx.dataStore.data.map { it[KEY_PHONE] ?: "" }
    fun genres(ctx: Context): Flow<List<String>> =
        ctx.dataStore.data.map { (it[KEY_GENRES] ?: "").split(',').filter { s -> s.isNotBlank() } }

    suspend fun saveUser(ctx: Context, nombre: String, correo: String, phone: String, genres: List<String>) {
        ctx.dataStore.edit {
            it[KEY_NOMBRE] = nombre
            it[KEY_CORREO] = correo
            it[KEY_PHONE]  = phone
            it[KEY_GENRES] = genres.joinToString(",")
            it[KEY_LOGGED] = true
        }
    }

    suspend fun clear(ctx: Context) {
        ctx.dataStore.edit {
            it[KEY_NOMBRE] = ""
            it[KEY_CORREO] = ""
            it[KEY_PHONE]  = ""
            it[KEY_GENRES] = ""
            it[KEY_LOGGED] = false
        }
    }
}


