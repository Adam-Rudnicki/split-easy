package com.mammuten.spliteasy.presentation.groups.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType
import com.mammuten.spliteasy.presentation.components.DefaultRadioButton

@Composable
fun GroupOrderSection(
    modifier: Modifier = Modifier,
    groupOrder: GroupOrder = GroupOrder.Date(OrderType.Descending),
    onOrderChange: (GroupOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Name",
                selected = groupOrder is GroupOrder.Name,
                onSelect = {
                    if (groupOrder !is GroupOrder.Name) {
                        onOrderChange(GroupOrder.Name(groupOrder.orderType))
                    }
                }
            )
            DefaultRadioButton(
                text = "Date",
                selected = groupOrder is GroupOrder.Date,
                onSelect = {
                    if (groupOrder !is GroupOrder.Date) {
                        onOrderChange(GroupOrder.Date(groupOrder.orderType))
                    }
                }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = groupOrder.orderType is OrderType.Ascending,
                onSelect = {
                    if (groupOrder.orderType !is OrderType.Ascending) {
                        onOrderChange(groupOrder.copy(OrderType.Ascending))
                    }
                }
            )
            DefaultRadioButton(
                text = "Descending",
                selected = groupOrder.orderType is OrderType.Descending,
                onSelect = {
                    if (groupOrder.orderType !is OrderType.Descending) {
                        onOrderChange(groupOrder.copy(OrderType.Descending))
                    }
                }
            )
        }
    }
}