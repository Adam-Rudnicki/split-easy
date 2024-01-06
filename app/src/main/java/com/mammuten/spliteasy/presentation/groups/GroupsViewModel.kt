package com.mammuten.spliteasy.presentation.groups

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases
) : ViewModel() {

    private val _state = mutableStateOf(GroupsState())
    val state: State<GroupsState> = _state

    private var getGroupsJob: Job? = null

    init {
        getGroups(GroupOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: GroupsEvent) {
        when (event) {
            is GroupsEvent.Order -> getGroups(event.groupOrder)
        }
    }

    private fun getGroups(groupOrder: GroupOrder) {
        getGroupsJob?.cancel()
        getGroupsJob = groupUseCases.getGroupsUseCase(groupOrder)
            .onEach { groups ->
                _state.value = state.value.copy(groups = groups, groupOrder = groupOrder)
            }.launchIn(viewModelScope)
    }
}