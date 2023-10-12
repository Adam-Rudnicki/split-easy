package com.mammuten.spliteasy.util.common

sealed class Resource<out R> {
    data class Success<out T>(val data: T? = null, val message: String? = null) : Resource<T>()
    data class Failure<out T>(
        val exception: Throwable? = null,
        val message: String? = null,
        val data: T? = null
    ) :
        Resource<T>()

    data class Loading<out T>(val isLoading: Boolean = true, val data: T? = null) : Resource<T>()
}