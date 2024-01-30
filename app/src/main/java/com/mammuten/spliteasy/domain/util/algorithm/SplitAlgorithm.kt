package com.mammuten.spliteasy.domain.util.algorithm

import com.mammuten.spliteasy.domain.model.Contribution

data class Payoff(
    val id: Int,
    var amount: Double
)

fun algorithm(contributions: List<Contribution>): Map<Int, List<Pair<Int, Double>>> {
    val payoffs: List<Payoff> = contributions
        .map { Payoff(it.memberId, it.amountOwed - it.amountPaid) }
        .sortedByDescending { it.amount }

    val result = mutableMapOf<Int, MutableList<Pair<Int, Double>>>()

    var payerIndex = 0
    var receiverIndex = payoffs.lastIndex
    var payer: Payoff
    var receiver: Payoff
    var amount: Double

    while (payerIndex <= receiverIndex) {
        payer = payoffs[payerIndex]
        receiver = payoffs[receiverIndex]
        amount = minOf(payer.amount, -receiver.amount)
        if (amount > 0) {
            result.getOrPut(payer.id) { mutableListOf() }.add(Pair(receiver.id, amount))
            payer.amount -= amount
            receiver.amount += amount
        }
        if (payer.amount == 0.0) payerIndex++
        if (receiver.amount == 0.0) receiverIndex--
    }

    return result
}

private fun main() {
    val listBill: MutableList<Contribution> = mutableListOf(
        Contribution(1, 1, 100.0, 50.0),
        Contribution(1, 2, 30.0, 10.0),
        Contribution(1, 3, 20.0, 20.0),
        Contribution(1, 4, 40.0, 10.0),
        Contribution(1, 5, 10.0, 50.0),
        Contribution(1, 6, 15.0, 50.0),
        Contribution(1, 7, 5.0, 25.0),
        Contribution(1, 8, 60.0, 60.0),
        Contribution(1, 9, 25.0, 50.0),
        Contribution(1, 10, 70.0, 50.0)
    )

    println(algorithm(listBill))
}
