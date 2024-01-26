package com.mammuten.spliteasy.presentation.bill_details.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.domain.util.order.OrderType
import com.mammuten.spliteasy.presentation.components.DefaultRadioButton

@Composable
fun ContributionOrderSection(
    modifier: Modifier = Modifier,
    contributionOrder: ContributionOrder = ContributionOrder.AmountPaid(OrderType.Ascending),
    onOrderChange: (ContributionOrder) -> Unit
) {
    Column(
        modifier = modifier,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    DefaultRadioButton(
                        text = "Amount Paid",
                        selected = contributionOrder is ContributionOrder.AmountPaid,
                        onSelect = { onOrderChange(ContributionOrder.AmountPaid(contributionOrder.orderType)) }
                    )
                    DefaultRadioButton(
                        text = "Amount Owed",
                        selected = contributionOrder is ContributionOrder.AmountOwed,
                        onSelect = { onOrderChange(ContributionOrder.AmountOwed(contributionOrder.orderType)) }
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    DefaultRadioButton(
                        text = "Ascending",
                        selected = contributionOrder.orderType is OrderType.Ascending,
                        onSelect = { onOrderChange(contributionOrder.copy(OrderType.Ascending)) }
                    )
                    DefaultRadioButton(
                        text = "Descending",
                        selected = contributionOrder.orderType is OrderType.Descending,
                        onSelect = { onOrderChange(contributionOrder.copy(OrderType.Descending)) }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun ContributionOrderSectionPreview() {
    ContributionOrderSection(onOrderChange = {})
}