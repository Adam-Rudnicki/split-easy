package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.domain.usecase.general.GeneralUseCases
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
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
class BillDetailsViewModel @Inject constructor(
    private val billUseCases: BillUseCases,
    private val contributionUseCases: ContributionUseCases,
    private val generalUseCases: GeneralUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(BillDetailsState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    private var recentlyDeletedContribution: Contribution? = null

    private var getBillJob: Job? = null
    private var getMembersAndContributionsJob: Job? = null

    init {
        getBill()
        getMemberAndContribution(state.contributionOrder)
    }

    fun onEvent(event: BillDetailsEvent) {
        when (event) {
            is BillDetailsEvent.DeleteBill -> {
                viewModelScope.launch {
                    state.bill?.let { bill ->
                        getBillJob?.cancel()
                        getMembersAndContributionsJob?.cancel()
                        billUseCases.deleteBillUseCase(bill)
                        _eventFlow.emit(UiEvent.DeleteBill)
                    }
                }
            }

            is BillDetailsEvent.DeleteContribution -> {
                viewModelScope.launch {
                    contributionUseCases.deleteContributionUseCase(event.contribution)
                    recentlyDeletedContribution = event.contribution
                    _eventFlow.emit(
                        UiEvent.ShowSnackbarRestoreContribution(
                            message = "Contribution deleted",
                            actionLabel = "Undo"
                        )
                    )
                }
            }

            is BillDetailsEvent.RestoreContribution -> {
                viewModelScope.launch {
                    contributionUseCases.upsertContributionUseCase(
                        recentlyDeletedContribution ?: return@launch
                    )
                    recentlyDeletedContribution = null
                }
            }

            is BillDetailsEvent.ContributionsOrder -> {
                if (state.contributionOrder != event.contributionOrder) {
                    getMemberAndContribution(event.contributionOrder)
                }
            }

            is BillDetailsEvent.NavigateToAddEditBillScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.AddEditBillScreen.route +
                                    "/${state.bill?.groupId}" +
                                    "?billId=${currentBillId}"
                        )
                    )
                }
            }

            is BillDetailsEvent.NavigateToManageContributionsScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.ManageContributionsScreen.route +
                                    "/${state.bill?.groupId}" +
                                    "/${currentBillId}"
                        )
                    )
                }
            }

            is BillDetailsEvent.NavigateToCalculateScreen -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.Navigate(
                            Screen.CalculateScreen.route + "/${currentBillId}"
                        )
                    )
                }
            }
        }
    }

    private fun getBill() {
        getBillJob?.cancel()
        getBillJob = billUseCases.getBillByIdUseCase(currentBillId)
            .onEach { bill ->
                state = state.copy(bill = bill)
            }.launchIn(viewModelScope)
    }

    private fun getMemberAndContribution(contributionOrder: ContributionOrder) {
        getMembersAndContributionsJob?.cancel()
        getMembersAndContributionsJob =
            generalUseCases.getMembersAndContributionsInBillUseCase(
                currentBillId, contributionOrder
            ).onEach { membersAndContributions ->
                state = state.copy(
                    membersAndContributions = membersAndContributions.toList(),
                    contributionOrder = contributionOrder
                )
            }.launchIn(viewModelScope)
    }

    sealed interface UiEvent {
        data class ShowSnackbarRestoreContribution(
            val message: String, val actionLabel: String? = null
        ) : UiEvent

        data object DeleteBill : UiEvent
        data class Navigate(val route: String) : UiEvent
    }
}