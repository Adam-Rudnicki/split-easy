package com.mammuten.spliteasy.presentation.manage_contributions

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.domain.usecase.general.GeneralUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageContributionsViewModel @Inject constructor(
    private val generalUseCases: GeneralUseCases,
    private val contributionUseCases: ContributionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var allMembersAndContributions: List<Pair<Member, Contribution?>> = emptyList()

    val state = mutableStateListOf<MemberState>()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val currentGroupId: Int = checkNotNull(savedStateHandle["groupId"])
    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    init {
        getAllMembersAndContributions()
    }

    fun onEvent(event: ManageContributionsEvent) {
        when (event) {
            is ManageContributionsEvent.EnteredAmountPaid -> {
                val index = state.indexOfFirst { it.member.id == event.memberId }
                state[index] = state[index].copy(
                    amountPaidState = TextFieldState(
                        value = event.value,
                        error = InvalidInputError.checkDecimal(
                            decimal = event.value.toDoubleOrNull(),
                            isRequired = false,
                            maxValue = Contribution.MAX_AMOUNT
                        )
                    )
                )
            }

            is ManageContributionsEvent.EnteredAmountOwed -> {
                val index = state.indexOfFirst { it.member.id == event.memberId }
                state[index] = state[index].copy(
                    amountOwedState = TextFieldState(
                        value = event.value,
                        error = InvalidInputError.checkDecimal(
                            decimal = event.value.toDoubleOrNull(),
                            isRequired = false,
                            maxValue = Contribution.MAX_AMOUNT
                        )
                    )
                )
            }

            is ManageContributionsEvent.SaveContributions -> {
                viewModelScope.launch {
                    if (!isSumValid()) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Sum of amount paid and owed must be equal"))
                        return@launch
                    }
                    if (state.any { it.amountPaidState.error != null || it.amountOwedState.error != null }) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Please fill all fields correctly"))
                        return@launch
                    }

                    val contributionsToUpsert = mutableListOf<Contribution>()
                    val contributionsToDelete = mutableListOf<Contribution>()

                    for (memberState in state) {
                        val contribution =
                            allMembersAndContributions.first { it.first.id == memberState.member.id }.second
                        val amountPaid = memberState.amountPaidState.value.toDoubleOrNull()
                        val amountOwed = memberState.amountOwedState.value.toDoubleOrNull()

                        val tempContribution: Contribution? = Contribution(
                            billId = currentBillId,
                            memberId = memberState.member.id!!,
                            amountPaid = amountPaid ?: 0.0,
                            amountOwed = amountOwed ?: 0.0
                        ).takeIf { it.amountPaid != it.amountOwed }

                        if (tempContribution == null) {
                            contribution?.let { contributionsToDelete.add(it) }
                        } else {
                            contribution?.let {
                                if (tempContribution != it) {
                                    contributionsToUpsert.add(tempContribution)
                                }
                            } ?: (contributionsToUpsert.add(tempContribution))
                        }
                    }

                    if (contributionsToUpsert.isNotEmpty() || contributionsToDelete.isNotEmpty()) {
                        contributionUseCases.updateContributionsUseCase(
                            contributionsToUpsert,
                            contributionsToDelete
                        )
                    }
                    _eventFlow.emit(UiEvent.SaveContributions)
                }
            }
        }
    }

    private fun getAllMembersAndContributions() {
        viewModelScope.launch {
            generalUseCases.getAllMembersInGroupAndContributionsInBillUseCase(
                currentGroupId, currentBillId
            ).firstOrNull()?.let {
                allMembersAndContributions =
                    it.toList().sortedWith(compareBy { (_, value) -> value == null })
            }

            state.addAll(allMembersAndContributions.map { (member, contribution) ->
                MemberState(
                    member = member,
                    amountPaidState = TextFieldState(
                        value = contribution?.amountPaid?.toString() ?: "",
                        error = InvalidInputError.checkDecimal(
                            decimal = contribution?.amountPaid,
                            isRequired = false,
                            maxValue = Contribution.MAX_AMOUNT
                        )
                    ),
                    amountOwedState = TextFieldState(
                        value = contribution?.amountOwed?.toString() ?: "",
                        error = InvalidInputError.checkDecimal(
                            decimal = contribution?.amountOwed,
                            isRequired = false,
                            maxValue = Contribution.MAX_AMOUNT
                        )
                    )
                )
            })
        }
    }

    private fun isSumValid(): Boolean {
        val sumOfAmountPaid =
            state.mapNotNull { it.amountPaidState.value.toDoubleOrNull() }.sumOf { it }
        val sumOfAmountOwed =
            state.mapNotNull { it.amountOwedState.value.toDoubleOrNull() }.sumOf { it }
        return sumOfAmountPaid == sumOfAmountOwed
    }

    data class MemberState(
        val member: Member,
        var amountPaidState: TextFieldState,
        var amountOwedState: TextFieldState,
    )

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveContributions : UiEvent()
    }
}



