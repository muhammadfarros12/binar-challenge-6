package com.farroos.movieapp_newfeatured.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

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

    val getStatus: Flow<Boolean> = context.dataStore.data
        .map {
            val myStatus = it[STATUS_KEY] ?: false
            myStatus
        }

    val getId: Flow<Int> = context.dataStore.data
        .map {
            val myId = it[ID] ?: 0
            myId
        }


    companion object {
        private val DATASTORE_NAME = "user_datastore_preference"
        private val ID = intPreferencesKey("id_key")
        private val STATUS_KEY = booleanPreferencesKey("status_key")
    }

}