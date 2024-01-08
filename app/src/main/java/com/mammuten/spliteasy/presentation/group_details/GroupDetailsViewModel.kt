package com.mammuten.spliteasy.presentation.group_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.usecase.member.MemberUseCases
import com.mammuten.spliteasy.domain.util.BillOrder
import com.mammuten.spliteasy.domain.util.MemberOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases,
    private val billUseCases: BillUseCases,
    private val memberUseCases: MemberUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(GroupDetailsState())
    val state: State<GroupDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])

    private var getGroupJob: Job? = null
    private var getBillsJob: Job? = null
    private var getMembersJob: Job? = null

    init {
        getGroup()
        getBills()
        getMembers()
    }

    fun onEvent(event: GroupDetailsEvent) {
        when (event) {
            is GroupDetailsEvent.DeleteGroup -> {
                viewModelScope.launch {
                    state.value.group?.let { group ->
                        getGroupJob?.cancel()
                        getBillsJob?.cancel()
                        getMembersJob?.cancel()
                        groupUseCases.deleteGroupUseCase(group)
                        _eventFlow.emit(UiEvent.DeleteGroup)
                    }
                }
            }

            is GroupDetailsEvent.DeleteMember -> {}

            is GroupDetailsEvent.Order -> getBills(event.billOrder)
        }
    }

    private fun getGroup() {
        getGroupJob?.cancel()
        getGroupJob = groupUseCases.getGroupByIdUseCase(currentGroupId)
            .onEach { group ->
                _state.value = state.value.copy(group = group)
            }.launchIn(viewModelScope)
    }

    private fun getBills(billOrder: BillOrder? = null) {
        getBillsJob?.cancel()
        getBillsJob = billUseCases.getBillsByGroupIdUseCase(currentGroupId, billOrder)
            .onEach { bills ->
                _state.value = state.value.copy(bills = bills, billOrder = billOrder)
            }.launchIn(viewModelScope)
    }

    private fun getMembers(memberOrder: MemberOrder? = null) {
        getMembersJob?.cancel()
        getMembersJob = memberUseCases.getMembersByGroupIdUseCase(currentGroupId, memberOrder)
            .onEach { members ->
                _state.value = state.value.copy(members = members, memberOrder = memberOrder)
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object DeleteGroup : UiEvent()
    }
}