package com.mammuten.spliteasy.presentation.add_edit_member

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
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMemberViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases,
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf(TextFieldState())
        private set

    var state by mutableStateOf(State())
        private set

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
                        name = name.copy(value = member.name)
                        member.userId?.let { userId ->
                            userUseCases.getUserByIdUseCase(userId).firstOrNull()?.let { user ->
                                state = state.copy(users = listOf(user), selectedUser = user)
                            }
                        }
                    }
                }
                name = name.copy(
                    error = InvalidInputError.checkText(
                        text = name.value,
                        isRequired = Member.IS_NAME_REQUIRED,
                        minLength = Member.MIN_NAME_LEN,
                        maxLength = Member.MAX_NAME_LEN
                    )
                )
            }

            getUsersNotInGroup()
        }
    }

    fun onEvent(event: AddEditMemberEvent) {
        when (event) {
            is AddEditMemberEvent.EnteredName -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = Member.IS_NAME_REQUIRED,
                    minLength = Member.MIN_NAME_LEN,
                    maxLength = Member.MAX_NAME_LEN
                )
                name = name.copy(value = event.value, error = error)
            }

            is AddEditMemberEvent.SaveMember -> {
                viewModelScope.launch {
                    if (name.error != null) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Please fill all fields correctly"))
                        return@launch
                    }
                    memberUseCases.upsertMemberUseCase(
                        Member(
                            id = currentMemberId,
                            groupId = currentGroupId,
                            userId = state.selectedUser?.id,
                            name = name.value.trim(),
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveMember)
                }
            }

            is AddEditMemberEvent.ToggleUserSelection -> {
                if (state.selectedUser != event.user) {
                    name = name.copy(value = event.user.name, error = null)
                }
                state =
                    state.copy(selectedUser = if (state.selectedUser == event.user) null else event.user)
            }
        }
    }

    private fun getUsersNotInGroup() {
        viewModelScope.launch {
            userUseCases.getUsersNotInGroupUseCase(currentGroupId).firstOrNull()?.let {
                state = state.copy(users = state.users.toMutableList().apply { addAll(it) })
            }
        }
    }

    data class State(
        val users: List<User> = emptyList(),
        val selectedUser: User? = null,
    )

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object SaveMember : UiEvent
    }
}
