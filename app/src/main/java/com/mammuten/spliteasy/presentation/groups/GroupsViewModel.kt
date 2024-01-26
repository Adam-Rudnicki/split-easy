package com.mammuten.spliteasy.presentation.groups

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.util.order.GroupOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases
) : ViewModel() {

    var state by mutableStateOf(GroupsState())
        private set

    private var getGroupsJob: Job? = null

    init {
        getGroups(state.groupOrder)
    }

    fun onEvent(event: GroupsEvent) {
        when (event) {
            is GroupsEvent.Order -> {
                if (state.groupOrder != event.groupOrder
                ) {
                    getGroups(event.groupOrder)
                }
            }
        }
    }
    //state.groupOrder::class != event.groupOrder::class ||
    private fun getGroups(groupOrder: GroupOrder) {
        getGroupsJob?.cancel()
        getGroupsJob = groupUseCases.getGroupsUseCase(groupOrder)
            .onEach { groups ->
                state = state.copy(groups = groups, groupOrder = groupOrder)
            }.launchIn(viewModelScope)
    }
}