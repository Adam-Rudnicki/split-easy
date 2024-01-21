package com.mammuten.spliteasy.presentation.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.user.UserUseCases
import com.mammuten.spliteasy.domain.util.order.UserOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    var state by mutableStateOf(UsersState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getUsersJob: Job? = null

    init {
        getUsers(state.userOrder)
    }

    fun onEvent(event: UsersEvent) {
        when (event) {
            is UsersEvent.DeleteUser -> {
                viewModelScope.launch {
                    userUseCases.deleteUserUseCase(event.user)
                }
            }

            is UsersEvent.Order -> {
                if (state.userOrder::class != event.userOrder::class ||
                    state.userOrder.orderType != event.userOrder.orderType
                ) {
                    getUsers(event.userOrder)
                }
            }
        }
    }

    private fun getUsers(userOrder: UserOrder) {
        getUsersJob?.cancel()
        getUsersJob = userUseCases.getUsersUseCase(userOrder)
            .onEach { users ->
                state = state.copy(users = users, userOrder = userOrder)
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}