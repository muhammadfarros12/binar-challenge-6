package com.farroos.movieapp_newfeatured.ui.home

import androidx.lifecycle.*
import com.farroos.movieapp_newfeatured.data.remote.home.Movie
import com.farroos.movie.data.resource.Resource
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.data.local.UserRepository
import com.farroos.movieapp_newfeatured.data.remote.ErrorMovie
import com.farroos.movieapp_newfeatured.data.remote.MovieRepository
import com.farroos.movieapp_newfeatured.utils.DataStoreRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository,
    private val userRepository: UserRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    private var _nowPlaying = MutableLiveData<List<Movie>>()
    val nowPlaying: LiveData<List<Movie>> get() = _nowPlaying

    private var _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> get() = _errorStatus

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _userData = MutableLiveData<Resource<User>>()
    val userData: LiveData<Resource<User>> get() = _userData

    init {
        getPlayingNowMovie()
    }

    private fun getPlayingNowMovie() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _nowPlaying.value = repository.getNowPlayingMovie()
            } catch (e: ErrorMovie) {
                _errorStatus.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun onSnackbar(){
        _errorStatus.value = null
    }

    fun userData(id: Int) {
        viewModelScope.launch {
            _userData.value = Resource.loading(null)
            try {
                val data = userRepository.getUser(id)
                _userData.value = Resource.success(data, 0)
            } catch (e: Exception) {
                _userData.value = e.message?.let { Resource.error(null, it) }
            }
        }
    }

    fun getIdUser(): LiveData<Int>{
        return dataStore.getId().asLiveData()
    }

}