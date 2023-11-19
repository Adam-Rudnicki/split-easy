package com.mammuten.spliteasy.util.common

typealias SimpleResource = Resource<Unit>

sealed class Resource<out T> {
    data class Success<T>(val data: T? = null, val message: String? = null) : Resource<T>()
    data class Failure<T>(
        val exception: Throwable? = null,
        val message: String? = null,
        val data: T? = null
    ) : Resource<T>()

    data class Loading<T>(val isLoading: Boolean = true, val data: T? = null) : Resource<T>()
}