package com.farroos.movieapp_newfeatured.utils

open class Event<out T>(private val content: T) {

    private var eventHandled = false
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