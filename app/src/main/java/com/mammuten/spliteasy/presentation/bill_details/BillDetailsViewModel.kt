package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.usecase.bill.BillUseCases
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.domain.usecase.member.MemberUseCases
import com.mammuten.spliteasy.domain.util.ContributionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillDetailsViewModel @Inject constructor(
    private val billUseCases: BillUseCases,
    private val contributionUseCases: ContributionUseCases,
    private val memberUseCases: MemberUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(BillDetailsState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    private var getBillJob: Job? = null
    private var getContributionsJob: Job? = null

    private var groupMembers: List<Member> = emptyList()

    init {
        getBill()
        getGroupMembers()
        getContributions()
    }

    fun onEvent(event: BillDetailsEvent) {
        when (event) {
            is BillDetailsEvent.DeleteBill -> {
                viewModelScope.launch {
                    state.bill?.let { bill ->
                        getBillJob?.cancel()
                        getContributionsJob?.cancel()
                        billUseCases.deleteBillUseCase(bill)
                        _eventFlow.emit(UiEvent.DeleteBill)
                    }
                }
            }
            // TODO
            is BillDetailsEvent.DeleteContribution -> {}
        }
    }

    private fun getBill() {
        getBillJob?.cancel()
        getBillJob = billUseCases.getBillByIdUseCase(currentBillId)
            .onEach { bill ->
                state = state.copy(bill = bill)
            }.launchIn(viewModelScope)
    }

    private fun getGroupMembers() {
        viewModelScope.launch {
            val groupId = state.bill?.groupId
            groupId?.let {
                val members = memberUseCases.getMembersByGroupIdUseCase(groupId).firstOrNull()
                members?.let { groupMembers = it }
            }
        }
    }

    private fun getContributions(contributionOrder: ContributionOrder? = null) {
        getContributionsJob?.cancel()
        getContributionsJob =
            contributionUseCases.getContributionsByBillIdUseCase(currentBillId, contributionOrder)
                .onEach { contributions ->
                    val membersWithContributions = groupMembers.filter { member ->
                        state.contributions.any { it.memberId == member.id }
                    }
                    val membersWithoutContributions = groupMembers.filter { member ->
                        state.contributions.none { it.memberId == member.id }
                    }
                    state = state.copy(
                        contributions = contributions,
                        contributionOrder = contributionOrder,
                        membersWithContributions = membersWithContributions,
                        membersWithoutContributions = membersWithoutContributions
                    )
                }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data object DeleteBill : UiEvent()
    }
}