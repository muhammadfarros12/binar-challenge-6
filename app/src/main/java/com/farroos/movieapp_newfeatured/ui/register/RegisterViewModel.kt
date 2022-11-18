package com.farroos.movieapp_newfeatured.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farroos.movieapp_newfeatured.utils.Event
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.data.local.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun save(userName: String,fullName: String,email: String,password: String,address: String, image: String){
        if (userName.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || image.isEmpty()){
            _saved.value = Event(false)
        }

        val user = User(
            username = userName,
            fullname = fullName,
            email = email,
            password = password,
            address = address,
            image = image
        )

        viewModelScope.launch {
            repository.save(user)
        }

        _saved.value = Event(true)

    }

}