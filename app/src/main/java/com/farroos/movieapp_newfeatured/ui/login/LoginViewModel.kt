@file:Suppress("unused", "unused")

package com.farroos.movieapp_newfeatured.ui.login

import androidx.lifecycle.*
import com.farroos.movie.data.resource.Resource
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.data.local.UserRepository
import com.farroos.movieapp_newfeatured.utils.DataStoreRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var _loginStatus = MutableLiveData<Resource<User>>()
    val loginStatus: LiveData<Resource<User>> get() = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginStatus.postValue(Resource.loading(null))
            try {
                val data = repository.verifyLogin(email, password)
                _loginStatus.postValue(Resource.success(data, 0))
            } catch (e: Exception){
                _loginStatus.postValue(e.message?.let { Resource.error(null, it) })
            }
        }
    }

    fun saveUserDataStore(status: Boolean, id: Int){
        viewModelScope.launch {
            dataStoreRepository.save(status, id)
        }
    }

    fun getStatus(): LiveData<Boolean> {
        return dataStoreRepository.getStatus().asLiveData()
    }


}