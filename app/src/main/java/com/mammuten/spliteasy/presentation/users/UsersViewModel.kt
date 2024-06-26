package com.mammuten.spliteasy.presentation.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.user.UserUseCases
import com.mammuten.spliteasy.domain.util.order.UserOrder
import com.mammuten.spliteasy.presentation.util.Screen
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
                if (state.userOrder != event.userOrder) {
                    getUsers(event.userOrder)
                }
            }

            is UsersEvent.NavigateToAddEditUserScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddEditUserScreen.route + "?userId=${event.userId}"))
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

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data class Navigate(val route: String) : UiEvent
    }
}