package com.mammuten.spliteasy.domain.util.algorithm

import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member

data class Payer(
    val payer: Member,
    val receivers: List<Receiver>
)

data class Receiver(
    val receiver: Member,
    val amount: Int
)

fun algorithm(contributions: List<Contribution>): Map<Int, List<Pair<Int, Int>>> {
    data class Payoff(val id: Int, var amount: Int)

    val payoffs: List<Payoff> = contributions
        .map { Payoff(it.memberId, it.amountOwed - it.amountPaid) }
        .sortedByDescending { it.amount }

    val result = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()

    var payerIndex = 0
    var receiverIndex = payoffs.lastIndex
    var payer: Payoff
    var receiver: Payoff
    var amount: Int

    while (payerIndex <= receiverIndex) {
        payer = payoffs[payerIndex]
        receiver = payoffs[receiverIndex]
        amount = minOf(payer.amount, -receiver.amount)
        if (amount > 0) {
            result.getOrPut(payer.id) { mutableListOf() }.add(Pair(receiver.id, amount))
            payer.amount -= amount
            receiver.amount += amount
        }
        if (payer.amount == 0) payerIndex++
        if (receiver.amount == 0) receiverIndex--
    }

    return result
}

private fun main() {
    val listBill: MutableList<Contribution> = mutableListOf(
        Contribution(1, 1, 10000, 5000),
        Contribution(1, 2, 3000, 1000),
        Contribution(1, 3, 4000, 1000),
        Contribution(1, 4, 1000, 5000),
        Contribution(1, 5, 1500, 5000),
        Contribution(1, 6, 500, 2500),
        Contribution(1, 7, 2500, 5000),
        Contribution(1, 8, 7000, 5000)
    )

    println(algorithm(listBill))
}
