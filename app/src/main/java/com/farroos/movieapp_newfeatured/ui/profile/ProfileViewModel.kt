package com.farroos.movieapp_newfeatured.ui.profile

import androidx.lifecycle.*
import com.farroos.movie.data.resource.Resource
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.data.local.UserRepository
import com.farroos.movieapp_newfeatured.utils.DataStoreRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val dataStore: DataStoreRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> get() = _errorStatus

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _userData = MutableLiveData<Resource<User>>()
    val userData: LiveData<Resource<User>> get() = _userData

    fun clearDataUser() {
        viewModelScope.launch {
            dataStore.logout()
        }
    }

    fun userData(id: Int) {
        viewModelScope.launch {
            _userData.postValue(Resource.loading(null))
            try {
                val data = userRepository.getUser(id)
                _userData.postValue(Resource.success(data, 0))
            } catch (e: Exception) {
                _userData.postValue(Resource.error(null, e.message!!))
            }
        }
    }

    fun getIdUser(): LiveData<Int> {
        return dataStore.getId().asLiveData()
    }

}