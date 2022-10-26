package com.farroos.movieapp_newfeatured.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    suspend fun save(status: Boolean, id: Int) {
        context.dataStore.edit {
            it[STATUS_KEY] = status
            it[ID] = id
        }
    }

    suspend fun logout() {
        context.dataStore.edit {
            it.remove(STATUS_KEY)
            it.remove(ID)
        }
    }

    fun getStatus(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[STATUS_KEY] ?: false

        }
    }

    fun getId(): Flow<Int> {
        return context.dataStore.data.map { data ->
            data[ID] ?: 0

        }
    }


    companion object {
        private const val DATASTORE_NAME = "user_datastore_preference"
        private val ID = intPreferencesKey("id_key")
        private val STATUS_KEY = booleanPreferencesKey("status_key")
    }

}