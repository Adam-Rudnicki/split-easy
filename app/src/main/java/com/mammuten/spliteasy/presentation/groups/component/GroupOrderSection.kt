//package com.mammuten.spliteasy.presentation.groups.component
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.mammuten.spliteasy.domain.util.order.GroupOrder
//import com.mammuten.spliteasy.domain.util.order.OrderType
//import com.mammuten.spliteasy.presentation.components.DefaultRadioButton
//
//@Composable
//fun GroupOrderSection(
//    modifier: Modifier = Modifier,
//    groupOrder: GroupOrder = GroupOrder.Date(OrderType.Descending),
//    onOrderChange: (GroupOrder) -> Unit
//) {
//    Column(
//        modifier = modifier,
//        content = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                content = {
//                    DefaultRadioButton(
//                        text = "Name",
//                        selected = groupOrder is GroupOrder.Name,
//                        onSelect = { onOrderChange(GroupOrder.Name(groupOrder.orderType)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Date",
//                        selected = groupOrder is GroupOrder.Date,
//                        onSelect = { onOrderChange(GroupOrder.Date(groupOrder.orderType)) }
//                    )
//                }
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                content = {
//                    DefaultRadioButton(
//                        text = "Ascending",
//                        selected = groupOrder.orderType is OrderType.Ascending,
//                        onSelect = { onOrderChange(groupOrder.copy(OrderType.Ascending)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Descending",
//                        selected = groupOrder.orderType is OrderType.Descending,
//                        onSelect = { onOrderChange(groupOrder.copy(OrderType.Descending)) }
//                    )
//                }
//            )
//        }
//    )
//}
//
//@Preview
//@Composable
//fun GroupOrderSectionPreview() {
//    GroupOrderSection(onOrderChange = {})
//}