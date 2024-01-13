package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _state = mutableStateOf(BillDetailsState())
    val state: State<BillDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    private var getBillJob: Job? = null
    private var getContributionsJob: Job? = null

    private var groupMembers: List<Member> = emptyList()

    init {
        getBill()
        getContributions()
        getGroupMembers()
    }

    fun onEvent(event: BillDetailsEvent) {
        when (event) {
            is BillDetailsEvent.DeleteBill -> {
                viewModelScope.launch {
                    state.value.bill?.let { bill ->
                        getBillJob?.cancel()
                        getContributionsJob?.cancel()
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

    private fun getContributions(contributionOrder: ContributionOrder? = null) {
        getContributionsJob?.cancel()
        getContributionsJob =
            contributionUseCases.getContributionsByBillIdUseCase(currentBillId, contributionOrder)
                .onEach { contributions ->
                    _state.value = state.value.copy(
                        contributions = contributions,
                        contributionOrder = contributionOrder
                    )
                }.launchIn(viewModelScope)
    }

    private fun getGroupMembers() {
        viewModelScope.launch {
            val groupId = state.value.bill?.groupId
            groupId?.let {
                val members = memberUseCases.getMembersByGroupIdUseCase(groupId).firstOrNull()
                members?.let {
                    groupMembers = it
                    filterMembersWithContributions()
                    filterMembersWithoutContributions()
                }
            }
        }
    }

    private fun filterMembersWithContributions() {
        val membersWithContributions = groupMembers.filter { member ->
            state.value.contributions.any { it.memberId == member.id }
        }
        _state.value = state.value.copy(membersWithContributions = membersWithContributions)
    }

    private fun filterMembersWithoutContributions() {
        val membersWithoutContributions = groupMembers.filter { member ->
            state.value.contributions.none { it.memberId == member.id }
        }
        _state.value = state.value.copy(membersWithoutContributions = membersWithoutContributions)
    }

    sealed class UiEvent {
        data object DeleteBill : UiEvent()
    }
}