package com.mammuten.spliteasy.presentation.add_edit_contribution

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditContributionViewModel @Inject constructor(
    private val contributionUseCases: ContributionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var memberId by mutableIntStateOf(-1)
        private set

    var amountPaid by mutableStateOf(TextFieldState())
        private set

    var amountOwed by mutableStateOf(TextFieldState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])
    private var currentMemberId: Int? = null

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("memberId")?.let { id ->
                if (id != -1) {
                    contributionUseCases.getContributionByBillIdAndMemberIdUseCase(
                        currentBillId, id
                    ).firstOrNull()?.let { contribution ->
                        currentMemberId = contribution.memberId
                        memberId = contribution.memberId
                        amountPaid = amountPaid.copy(value = contribution.amountPaid.toString())
                        amountOwed = amountOwed.copy(value = contribution.amountOwed.toString())
                    }
                }
            }
            amountPaid = amountPaid.copy(value = "0.0")
            amountPaid = amountPaid.copy(
                error = InvalidInputError.checkDecimal(
                    decimal = amountPaid.value.toDoubleOrNull(),
                    isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    maxValue = Contribution.MAX_AMOUNT
                )
            )
            amountOwed = amountOwed.copy(value = "0.0")
            amountOwed = amountOwed.copy(
                error = InvalidInputError.checkDecimal(
                    decimal = amountOwed.value.toDoubleOrNull(),
                    isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    maxValue = Contribution.MAX_AMOUNT
                )
            )
        }
    }

    fun onEvent(event: AddEditContributionEvent) {
        when (event) {
            is AddEditContributionEvent.EnteredMemberId -> {
                memberId = event.value
            }

            is AddEditContributionEvent.EnteredAmountPaid -> {
                val error: InvalidInputError? = InvalidInputError.checkDecimal(
                    decimal = event.value.toDoubleOrNull(),
                    isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    maxValue = Contribution.MAX_AMOUNT
                )
                amountPaid = amountPaid.copy(value = event.value, error = error)
            }

            is AddEditContributionEvent.EnteredAmountOwed -> {
                val error: InvalidInputError? = InvalidInputError.checkDecimal(
                    decimal = event.value.toDoubleOrNull(),
                    isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    maxValue = Contribution.MAX_AMOUNT
                )
                amountOwed = amountOwed.copy(value = event.value, error = error)
            }

            is AddEditContributionEvent.SaveContribution -> {
                viewModelScope.launch {
                    if (amountPaid.error != null || amountOwed.error != null) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Please fill all fields correctly"))
                        return@launch
                    }
                    val contribution = Contribution(
                        billId = currentBillId,
                        memberId = memberId,
                        amountPaid = amountPaid.value.toDouble(),
                        amountOwed = amountOwed.value.toDouble()
                    )
                    if (currentMemberId != null && currentMemberId != memberId) {
                        contributionUseCases.deleteContributionUseCase(
                            contribution.copy(memberId = currentMemberId!!)
                        )
                    }
                    contributionUseCases.upsertContributionUseCase(contribution)
                    _eventFlow.emit(UiEvent.SaveContribution)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveContribution : UiEvent()
    }
}
