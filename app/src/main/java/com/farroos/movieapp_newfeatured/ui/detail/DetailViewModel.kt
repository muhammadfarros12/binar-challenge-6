package com.farroos.movieapp_newfeatured.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.farroos.movie.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.data.remote.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    private var _detail = MutableLiveData<DetailMovie?>()
    val detail : LiveData<DetailMovie?> get() = _detail

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getDetail(id: Int){
        ApiClient.instance.getDetailMovie(id).enqueue(object : Callback<DetailMovie>{
            override fun onResponse(call: Call<DetailMovie>, response: Response<DetailMovie>) {
                _loading.value = true
                when {
                    response.code() == 200 -> {
                        _loading.value = false
                        _detail.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<DetailMovie>, t: Throwable) {
                _loading.value = false
            }

        })
    }

}