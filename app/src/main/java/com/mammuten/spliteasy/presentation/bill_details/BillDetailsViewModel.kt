package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillDetailsViewModel @Inject constructor(
    private val billUseCases: BillUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(BillDetailsState())
    val state: State<BillDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    private var getBillJob: Job? = null

    init {
        getBill()
    }

    fun onEvent(event: BillDetailsEvent) {
        when (event) {
            is BillDetailsEvent.DeleteBill -> {
                viewModelScope.launch {
                    state.value.bill?.let { bill ->
                        getBillJob?.cancel()
                        billUseCases.deleteBillUseCase(bill)
                        _eventFlow.emit(UiEvent.DeleteBill)
                    }
                }
            }
        }
    }

    private fun getBill() {
        getBillJob?.cancel()
        getBillJob = billUseCases.getBillByIdUseCase(currentBillId)
            .onEach { bill ->
                _state.value = state.value.copy(bill = bill)
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data object DeleteBill : UiEvent()
    }
}