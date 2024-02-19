package com.mammuten.spliteasy.presentation.add_edit_bill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.DateState
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBillViewModel @Inject constructor(
    private val billUseCases: BillUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf(TextFieldState())
        private set

    var description by mutableStateOf(TextFieldState())
        private set

    var date by mutableStateOf(DateState())
        private set

    var isSaving by mutableStateOf(false)
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])
    private var currentBillId: Int? = null

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("billId")?.let { id ->
                if (id != -1) {
                    billUseCases.getBillByIdUseCase(id).firstOrNull()?.let { bill ->
                        currentBillId = bill.id
                        name = name.copy(value = bill.name)
                        bill.description?.let { description = description.copy(value = it) }
                        date = date.copy(value = bill.date)
                    }
                }
            }
            name = name.copy(
                error = InvalidInputError.checkText(
                    text = name.value,
                    isRequired = Bill.IS_NAME_REQUIRED,
                    minLength = Bill.MIN_NAME_LEN,
                    maxLength = Bill.MAX_NAME_LEN
                )
            )
            description = description.copy(
                error = InvalidInputError.checkText(
                    text = description.value,
                    isRequired = Bill.IS_DESC_REQUIRED,
                    minLength = Bill.MIN_DESC_LEN,
                    maxLength = Bill.MAX_DESC_LEN
                )
            )
            date = date.copy(
                error = InvalidInputError.checkDate(
                    date = date.value,
                    isRequired = Bill.IS_DATE_REQUIRED
                )
            )
        }
    }

    fun onEvent(event: AddEditBillEvent) {
        when (event) {
            is AddEditBillEvent.EnteredName -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = Bill.IS_NAME_REQUIRED,
                    minLength = Bill.MIN_NAME_LEN,
                    maxLength = Bill.MAX_NAME_LEN
                )
                name = name.copy(value = event.value, error = error)
            }

            is AddEditBillEvent.EnteredDescription -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = Bill.IS_DESC_REQUIRED,
                    minLength = Bill.MIN_DESC_LEN,
                    maxLength = Bill.MAX_DESC_LEN
                )
                description = description.copy(value = event.value, error = error)
            }

            is AddEditBillEvent.EnteredDate -> {
                val error: InvalidInputError? = InvalidInputError.checkDate(
                    date = event.value,
                    isRequired = Bill.IS_DATE_REQUIRED
                )
                date = date.copy(value = event.value, error = error)
            }

            is AddEditBillEvent.SaveBill -> {
                viewModelScope.launch {
                    if (name.error != null || description.error != null || date.error != null) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Please fill all fields correctly"))
                        return@launch
                    }
                    isSaving = true
                    billUseCases.upsertBillUseCase(
                        Bill(
                            id = currentBillId,
                            groupId = currentGroupId,
                            name = name.value.trim(),
                            description = description.value.takeIf { it.isNotBlank() }?.trim(),
                            date = date.value
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveBill)
                }
            }
        }
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object SaveBill : UiEvent
    }
}