package com.mammuten.spliteasy.presentation.manage_contributions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.usecase.contribution.ContributionUseCases
import com.mammuten.spliteasy.domain.usecase.general.GeneralUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import com.mammuten.spliteasy.presentation.util.PriceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class ManageContributionsViewModel @Inject constructor(
    private val generalUseCases: GeneralUseCases,
    private val contributionUseCases: ContributionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var allMembersAndContributions: List<Pair<Member, Contribution?>> = emptyList()

    var state by mutableStateOf(ManageContributionsState())
        private set

    var isSaving by mutableStateOf(false)
        private set

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
                val index = state.memberStates.indexOfFirst { it.member.id == event.memberId }
                val updatedMemberStates = state.memberStates.toMutableList()
                updatedMemberStates[index] = updatedMemberStates[index].copy(
                    amountPaidState = TextFieldState(
                        value = event.value,
                        error = InvalidInputError.checkAmount(
                            amount = PriceUtil.scaleAndMultiply(event.value.toDoubleOrNull()),
                            isRequired = false,
                            maxAmount = Contribution.MAX_AMOUNT
                        )
                    )
                )
                state = ManageContributionsState(updatedMemberStates)
            }

            is ManageContributionsEvent.EnteredAmountOwed -> {
                val index = state.memberStates.indexOfFirst { it.member.id == event.memberId }
                val updatedMemberStates = state.memberStates.toMutableList()
                updatedMemberStates[index] = updatedMemberStates[index].copy(
                    amountOwedState = TextFieldState(
                        value = event.value,
                        error = InvalidInputError.checkAmount(
                            amount = PriceUtil.scaleAndMultiply(event.value.toDoubleOrNull()),
                            isRequired = false,
                            maxAmount = Contribution.MAX_AMOUNT
                        )
                    )
                )
                state = ManageContributionsState(updatedMemberStates)
            }

            is ManageContributionsEvent.SaveContributions -> {
                viewModelScope.launch {
                    if (state.memberStates.any { it.amountPaidState.error != null || it.amountOwedState.error != null }) {
                        _eventFlow.emit(UiEvent.ShowSnackbar("Please fill all fields correctly"))
                        return@launch
                    }
                    isSaving = true

                    val contributionsToUpsert = mutableListOf<Contribution>()
                    val contributionsToDelete = mutableListOf<Contribution>()

                    for (memberState in state.memberStates) {
                        val contribution =
                            allMembersAndContributions.first { it.first.id == memberState.member.id }.second
                        val amountPaid =
                            PriceUtil.scaleAndMultiply(memberState.amountPaidState.value.toDoubleOrNull())
                        val amountOwed =
                            PriceUtil.scaleAndMultiply(memberState.amountOwedState.value.toDoubleOrNull())

                        val tempContribution: Contribution? = Contribution(
                            billId = currentBillId,
                            memberId = memberState.member.id!!,
                            amountPaid = amountPaid,
                            amountOwed = amountOwed
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

            state =
                ManageContributionsState(allMembersAndContributions.map { (member, contribution) ->
                    MemberState(
                        member = member,
                        amountPaidState = TextFieldState(
                            value = contribution?.amountPaid?.let {
                                PriceUtil.scaleAndDivide(it)
                            } ?: "",
                            error = InvalidInputError.checkAmount(
                                amount = contribution?.amountPaid,
                                isRequired = false,
                                maxAmount = Contribution.MAX_AMOUNT
                            )
                        ),
                        amountOwedState = TextFieldState(
                            value = contribution?.amountOwed?.let {
                                PriceUtil.scaleAndDivide(it)
                            } ?: "",
                            error = InvalidInputError.checkAmount(
                                amount = contribution?.amountOwed,
                                isRequired = false,
                                maxAmount = Contribution.MAX_AMOUNT
                            )
                        )
                    )
                })
        }
    }

    data class MemberState(
        val member: Member,
        var amountPaidState: TextFieldState,
        var amountOwedState: TextFieldState,
    )

    data class ManageContributionsState(
        val memberStates: List<MemberState> = emptyList()
    ) {
        val sumOfAmountPaid: Int
            get() = memberStates.sumOf {
                PriceUtil.scaleAndMultiply(it.amountPaidState.value.toDoubleOrNull())
            }

        val sumOfAmountOwed: Int
            get() = memberStates.sumOf {
                PriceUtil.scaleAndMultiply(it.amountOwedState.value.toDoubleOrNull())
            }

        val absoluteDifference: Int
            get() = abs(sumOfAmountPaid - sumOfAmountOwed)
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object SaveContributions : UiEvent
    }
}



