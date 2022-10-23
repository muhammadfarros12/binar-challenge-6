package com.farroos.movieapp_newfeatured.utils

import android.app.Application
import com.farroos.movieapp_newfeatured.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    userDataStoreModule,
                    remoteDataSourceModule,
                    repositoryModuleMovie,
                    viewModelModule,
                    repositoryModuleUser
                )
            )
        }

    }

}