package com.mammuten.spliteasy.presentation.group_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.domain.util.order.BillOrder
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
class GroupDetailsViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases,
    private val billUseCases: BillUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(GroupDetailsState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])

    private var getGroupJob: Job? = null
    private var getBillsJob: Job? = null

    init {
        getGroup()
        getBills(state.billOrder)
    }

    fun onEvent(event: GroupDetailsEvent) {
        when (event) {
            is GroupDetailsEvent.DeleteGroup -> {
                viewModelScope.launch {
                    state.group?.let { group ->
                        getGroupJob?.cancel()
                        getBillsJob?.cancel()
                        groupUseCases.deleteGroupUseCase(group)
                        _eventFlow.emit(UiEvent.DeleteGroup)
                    }
                }
            }

            is GroupDetailsEvent.BillsOrder -> {
                if (state.billOrder != event.billOrder) {
                    getBills(event.billOrder)
                }
            }

            is GroupDetailsEvent.NavigateToAddEditGroupScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddEditGroupScreen.route + "?groupId=${currentGroupId}"))
                }
            }

            is GroupDetailsEvent.NavigateToAddEditBillScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.AddEditBillScreen.route + "/${currentGroupId}"))
                }
            }

            is GroupDetailsEvent.NavigateToBillDetailsScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.BillDetailsScreen.route + "/${event.billId}"))
                }
            }
        }
    }

    private fun getGroup() {
        getGroupJob?.cancel()
        getGroupJob = groupUseCases.getGroupByIdUseCase(currentGroupId)
            .onEach { group ->
                state = state.copy(group = group)
            }.launchIn(viewModelScope)
    }

    private fun getBills(billOrder: BillOrder) {
        getBillsJob?.cancel()
        getBillsJob = billUseCases.getBillsByGroupIdUseCase(currentGroupId, billOrder)
            .onEach { bills ->
                state = state.copy(bills = bills, billOrder = billOrder)
            }.launchIn(viewModelScope)
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object DeleteGroup : UiEvent
        data class Navigate(val route: String) : UiEvent
    }
}