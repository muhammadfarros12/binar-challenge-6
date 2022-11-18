package com.farroos.movieapp_newfeatured.ui.profile.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farroos.movieapp_newfeatured.utils.Event
import com.farroos.movieapp_newfeatured.data.local.User
import com.farroos.movieapp_newfeatured.data.local.UserRepository
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val repository: UserRepository) : ViewModel() {

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun update(user: User) {
        if (user.email.isEmpty() || user.image!!.isEmpty() || user.username.isEmpty() || user.fullname.isEmpty() || user.address.isEmpty() || user.id == 0 || user.password.isEmpty()) {

            _saved.value = Event(false)

        } else {
            _saved.value = Event(true)
            viewModelScope.launch {
                repository.update(user)
            }
        }
    }

}