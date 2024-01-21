package com.mammuten.spliteasy.presentation.users.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mammuten.spliteasy.domain.util.order.OrderType
import com.mammuten.spliteasy.domain.util.order.UserOrder
import com.mammuten.spliteasy.presentation.components.DefaultRadioButton

@Composable
fun UserOrderSection(
    modifier: Modifier = Modifier,
    userOrder: UserOrder = UserOrder.Name(OrderType.Ascending),
    onOrderChange: (UserOrder) -> Unit
) {
    Column(
        modifier = modifier,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    DefaultRadioButton(
                        text = "Name",
                        selected = userOrder is UserOrder.Name,
                        onSelect = { onOrderChange(UserOrder.Name(userOrder.orderType)) }
                    )
                    DefaultRadioButton(
                        text = "Surname",
                        selected = userOrder is UserOrder.Surname,
                        onSelect = { onOrderChange(UserOrder.Surname(userOrder.orderType)) }
                    )
                    DefaultRadioButton(
                        text = "Nick",
                        selected = userOrder is UserOrder.Nick,
                        onSelect = { onOrderChange(UserOrder.Nick(userOrder.orderType)) }
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    DefaultRadioButton(
                        text = "Ascending",
                        selected = userOrder.orderType is OrderType.Ascending,
                        onSelect = { onOrderChange(userOrder.copy(OrderType.Ascending)) }
                    )
                    DefaultRadioButton(
                        text = "Descending",
                        selected = userOrder.orderType is OrderType.Descending,
                        onSelect = { onOrderChange(userOrder.copy(OrderType.Descending)) }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun UserOrderSectionPreview() {
    UserOrderSection(onOrderChange = {})
}