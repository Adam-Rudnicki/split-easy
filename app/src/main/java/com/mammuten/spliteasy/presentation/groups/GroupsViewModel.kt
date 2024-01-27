package com.mammuten.spliteasy.presentation.groups

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.util.order.GroupOrder
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
class GroupsViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases
) : ViewModel() {

    var state by mutableStateOf(GroupsState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getGroupsJob: Job? = null

    init {
        getGroups(state.groupOrder)
    }

    fun onEvent(event: GroupsEvent) {
        when (event) {
            is GroupsEvent.Order -> {
                if (state.groupOrder != event.groupOrder) {
                    getGroups(event.groupOrder)
                }
            }

            is GroupsEvent.NavigateToUsersScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.UsersScreen.route))
                }
            }

            is GroupsEvent.NavigateToAddEditGroupScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddEditGroupScreen.route))
                }
            }

            is GroupsEvent.NavigateToGroupDetailsScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.GroupDetailsScreen.route + "/${event.groupId}"))
                }
            }
        }
    }

    private fun getGroups(groupOrder: GroupOrder) {
        getGroupsJob?.cancel()
        getGroupsJob = groupUseCases.getGroupsUseCase(groupOrder)
            .onEach { groups ->
                state = state.copy(groups = groups, groupOrder = groupOrder)
            }.launchIn(viewModelScope)
    }

    sealed interface UiEvent {
        data class Navigate(val route: String) : UiEvent
    }
}