package com.farroos.movieapp_newfeatured.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farroos.movie.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.data.remote.ErrorMovie
import com.farroos.movieapp_newfeatured.data.remote.MovieRepository
import com.farroos.movieapp_newfeatured.data.remote.service.ApiClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val repository: MovieRepository): ViewModel() {

    private var _detail = MutableLiveData<DetailMovie?>()
    val detail : LiveData<DetailMovie?> get() = _detail

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private var _errorStatus = MutableLiveData<String?>()
    val errorStatus: LiveData<String?> = _errorStatus

    fun getDetail(id: Int){
        viewModelScope.launch {
            try {
                _loading.value = true
                _detail.value = repository.getDetail(id)
            } catch (e: ErrorMovie){
                _errorStatus.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun onSnackBar(){
        _errorStatus.value = null
    }

}