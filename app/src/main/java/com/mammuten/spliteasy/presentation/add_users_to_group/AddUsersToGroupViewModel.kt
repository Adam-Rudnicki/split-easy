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
import com.mammuten.spliteasy.presentation.add_edit_member.AddEditMemberEvent
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

    var users = List<User> = emptyList()
        private set

    data class State(
        val usersNotInGroup: List<User> = emptyList(),
        val selectedUsers: MutableMap<User, Boolean> = mutableMapOf()
    )

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])
    private var currentMemberId: Int? = null

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("memberId")?.let { id ->
                if (id != -1) {
                    memberUseCases.getMemberByIdUseCase(id).firstOrNull()?.let { member ->
                        currentMemberId = member.id
                        member.userId?.let { userId ->
                            userUseCases.getUserByIdUseCase(userId).firstOrNull()?.let { user ->
                                state = state.copy(
                                    usersNotInGroup = listOf(user),
                                    selectedUsers = mutableMapOf(user to true)
                                )
                            }
                        }
                    }
                }
            }

            getUsersNotInGroup()
        }
    }

    fun onEvent(event: AddEditMemberEvent) {
        when (event) {
            is AddEditMemberEvent.SaveMember -> {
                viewModelScope.launch {
                    val selectedUsers = state.selectedUsers.filter { it.value }.keys.toList()

                    if (selectedUsers.isNotEmpty()) {
                        selectedUsers.forEach { user ->
                            val member = Member(
                                id = null,
                                groupId = currentGroupId,
                                userId = user.id,
                                name = user.name
                            )

                            // Tutaj możesz użyć 'member' do dodania jednego zaznaczonego użytkownika do memberów
                            memberUseCases.upsertMemberUseCase(member)
                        }

//                        _eventFlow.emit(UiEvent.SaveMember("Members saved successfully"))
                    } else {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Select at least one user"))
                    }
                }
            }

            is AddEditMemberEvent.ToggleUserSelection -> {
                val newSelectedUsers = state.selectedUsers.toMutableMap()
//                newSelectedUsers[event.user] = event.isChecked
                state = state.copy(selectedUsers = newSelectedUsers)
            }

            else -> {}
        }
    }

    private fun getUsersNotInGroup() {
        viewModelScope.launch {
            userUseCases.getUsersNotInGroupUseCase(currentGroupId).firstOrNull()?.let { users ->
                val usersNotInGroup = users.toMutableList()
                state.selectedUsers.keys.let { usersNotInGroup.addAll(it) }
                state = state.copy(usersNotInGroup = usersNotInGroup)
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveUsers : UiEvent()
    }
}