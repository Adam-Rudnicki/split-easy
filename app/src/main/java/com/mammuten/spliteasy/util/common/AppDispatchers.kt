package com.mammuten.spliteasy.util.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    // access the Main thread on Android only for interacting with the UI and performing quick work
    // examples: suspend functions, running Android UI framework operations and updating Flow objects
    val main: CoroutineDispatcher = Dispatchers.Main,
    // use for disk or network I/O outside of the main thread
    // examples: using Room component, reading/writing files, running network operations
    val io: CoroutineDispatcher = Dispatchers.IO,
    // use for CPU-intensive operations
    // examples: sorting a list, parsing JSON
    val default: CoroutineDispatcher = Dispatchers.Default,
    // use when we do not care where the coroutine will be executed
    val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
)
