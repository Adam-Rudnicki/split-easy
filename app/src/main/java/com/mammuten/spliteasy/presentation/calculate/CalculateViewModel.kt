package com.mammuten.spliteasy.presentation.calculate

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.usecase.general.GeneralUseCases
import com.mammuten.spliteasy.domain.util.algorithm.Payer
import com.mammuten.spliteasy.domain.util.algorithm.Receiver
import com.mammuten.spliteasy.domain.util.algorithm.algorithm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(
    private val generalUseCases: GeneralUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(State())
        private set

    private val currentBillId: Int = checkNotNull(savedStateHandle["billId"])

    init {
        viewModelScope.launch {
            generalUseCases.getMembersAndContributionsInBillUseCase(currentBillId)
                .firstOrNull()?.let { it ->
                    val payoffs = algorithm(it.values.toList())
                    state = state.copy(
                        payoffs = it.filter { (member, _) -> member.id in payoffs }
                            .map { (payer, _) ->
                                Payer(
                                    payer = payer,
                                    receivers = payoffs[payer.id]!!.map { (receivedId, amount) ->
                                        Receiver(
                                            receiver = it.keys.first { it.id == receivedId },
                                            amount = amount
                                        )
                                    }
                                )
                            }
                    )
                }
        }
    }

    data class State(
        val payoffs: List<Payer> = emptyList()
    )
}