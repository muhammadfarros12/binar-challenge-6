package com.farroos.movieapp_newfeatured.di

import com.farroos.movieapp_newfeatured.data.local.UserRepository
import com.farroos.movieapp_newfeatured.data.remote.MovieRemoteDataSource
import com.farroos.movieapp_newfeatured.data.remote.MovieRepository
import com.farroos.movieapp_newfeatured.ui.detail.DetailViewModel
import com.farroos.movieapp_newfeatured.ui.home.HomeViewModel
import com.farroos.movieapp_newfeatured.ui.login.LoginViewModel
import com.farroos.movieapp_newfeatured.ui.profile.ProfileViewModel
import com.farroos.movieapp_newfeatured.ui.profile.update.UpdateProfileViewModel
import com.farroos.movieapp_newfeatured.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { MovieRemoteDataSource() }
}

val repositoryModuleUser = module {
    single { UserRepository(get()) }
}

val repositoryModuleMovie = module {
    single { MovieRepository(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { UpdateProfileViewModel(get()) }
}