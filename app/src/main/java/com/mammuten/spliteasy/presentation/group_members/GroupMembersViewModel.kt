package com.mammuten.spliteasy.presentation.group_members

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.usecase.member.MemberUseCases
import com.mammuten.spliteasy.domain.util.MemberHasContributions
import com.mammuten.spliteasy.domain.util.order.MemberOrder
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
class GroupMembersViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(GroupMembersState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])

    private var recentlyDeletedMember: Member? = null

    private var getMembersJob: Job? = null

    init {
        getMembers(state.memberOrder)
    }

    fun onEvent(event: GroupMembersEvent) {
        when (event) {
            is GroupMembersEvent.DeleteMember -> {
                viewModelScope.launch {
                    try {
                        memberUseCases.deleteMemberUseCase(event.member)
                        recentlyDeletedMember = event.member
                        _eventFlow.emit(UiEvent.ShowSnackbarRestoreMember("Member deleted", "Undo"))
                    } catch (e: MemberHasContributions) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(e.message!!))
                    }
                }
            }

            is GroupMembersEvent.RestoreMember -> {
                viewModelScope.launch {
                    memberUseCases.upsertMemberUseCase(recentlyDeletedMember ?: return@launch)
                    recentlyDeletedMember = null
                }
            }

            is GroupMembersEvent.MembersOrder -> {
                if (state.memberOrder::class != event.memberOrder::class
                ) {
                    getMembers(event.memberOrder)
                }
            }

            is GroupMembersEvent.NavigateToAddEditMemberScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.AddEditMemberScreen.route +
                                    "/${currentGroupId}" +
                                    "?memberId=${event.memberId}"
                        )
                    )
                }
            }

            is GroupMembersEvent.NavigateToAddMemberScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddEditMemberScreen.route + "/${currentGroupId}"))
                }
            }

            is GroupMembersEvent.NavigateToAddUsersScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddUsersToGroupScreen.route + "/${currentGroupId}"))
                }
            }
        }
    }

    private fun getMembers(memberOrder: MemberOrder) {
        getMembersJob?.cancel()
        getMembersJob = memberUseCases.getMembersByGroupIdUseCase(currentGroupId, memberOrder)
            .onEach { members ->
                state = state.copy(members = members, memberOrder = memberOrder)
            }.launchIn(viewModelScope)
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data class ShowSnackbarRestoreMember(
            val message: String, val actionLabel: String? = null
        ) : UiEvent

        data class Navigate(val route: String) : UiEvent
    }
}