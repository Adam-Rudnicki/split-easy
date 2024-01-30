package com.mammuten.spliteasy.presentation.add_users_to_group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.usecase.member.MemberUseCases
import com.mammuten.spliteasy.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUsersToGroupViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases,
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(State())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])

    init {
        getUsersNotInGroup()
    }

    fun onEvent(event: AddUsersToGroupEvent) {
        when (event) {
            is AddUsersToGroupEvent.SaveUsers -> {
                viewModelScope.launch {
                    val selectedUsers = state.selectedUsers.filter { it.value }.keys
                    if (selectedUsers.isNotEmpty()) {
                        val members = selectedUsers.map {
                            Member(
                                groupId = currentGroupId,
                                userId = it.id,
                                name = it.name
                            )
                        }
                        memberUseCases.upsertMembersUseCase(members)
                        _eventFlow.emit(UiEvent.SaveUsersToGroup)
                    } else {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Select at least one user"))
                    }
                }
            }

            is AddUsersToGroupEvent.ToggleUserSelection -> {
                val newSelectedUsers = state.selectedUsers.toMutableMap()
                newSelectedUsers[event.user] = event.isChecked
                state = state.copy(selectedUsers = newSelectedUsers)
            }
        }
    }

    private fun getUsersNotInGroup() {
        viewModelScope.launch {
            userUseCases.getUsersNotInGroupUseCase(currentGroupId).firstOrNull()?.let {
                state = state.copy(users = it, selectedUsers = it.associateWith { false })
            }
        }
    }

    data class State(
        val users: List<User> = emptyList(),
        val selectedUsers: Map<User, Boolean> = mapOf()
    )

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object SaveUsersToGroup : UiEvent
    }
}