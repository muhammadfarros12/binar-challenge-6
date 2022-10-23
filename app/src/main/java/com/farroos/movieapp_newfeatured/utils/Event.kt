package com.farroos.movie.utils

open class Event<out T>(private val content: T) {

    var eventHandled = false
        private set

    fun getContentIffNotHandled(): T? {
        return if (eventHandled) {
            null
        } else {
            eventHandled = true
            content
        }
    }

}