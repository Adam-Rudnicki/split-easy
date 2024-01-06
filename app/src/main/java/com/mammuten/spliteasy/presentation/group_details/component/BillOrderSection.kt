package com.mammuten.spliteasy.presentation.group_details.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mammuten.spliteasy.domain.util.BillOrder
import com.mammuten.spliteasy.domain.util.OrderType
import com.mammuten.spliteasy.presentation.components.DefaultRadioButton

@Composable
fun BillOrderSection(
    modifier: Modifier = Modifier,
    billOrder: BillOrder? = null,
    onOrderChange: (BillOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Name",
                selected = billOrder is BillOrder.Name,
                onSelect = {
                    if (billOrder !is BillOrder.Name) {
                        if (billOrder != null) {
                            onOrderChange(BillOrder.Name(billOrder.orderType))
                        }
                    }
                }
            )
            DefaultRadioButton(
                text = "Date",
                selected = billOrder is BillOrder.Date,
                onSelect = {
                    if (billOrder !is BillOrder.Date) {
                        if (billOrder != null) {
                            onOrderChange(BillOrder.Date(billOrder.orderType))
                        }
                    }
                }
            )
            DefaultRadioButton(
                text = "Amount",
                selected = billOrder is BillOrder.Amount,
                onSelect = {
                    if (billOrder !is BillOrder.Amount) {
                        if (billOrder != null) {
                            onOrderChange(BillOrder.Amount(billOrder.orderType))
                        }
                    }
                }
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (billOrder != null) {
            DefaultRadioButton(
                text = "Ascending",
                selected = billOrder.orderType is OrderType.Ascending,
                onSelect = {
                    if (billOrder.orderType !is OrderType.Ascending) {
                        onOrderChange(billOrder.copy(OrderType.Ascending))
                    }
                }
            )
        }
        if (billOrder != null) {
            DefaultRadioButton(
                text = "Descending",
                selected = billOrder.orderType is OrderType.Descending,
                onSelect = {
                    if (billOrder.orderType !is OrderType.Descending) {
                        onOrderChange(billOrder.copy(OrderType.Descending))
                    }
                }
            )
        }
    }
}