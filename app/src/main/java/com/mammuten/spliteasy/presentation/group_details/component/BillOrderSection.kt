//package com.mammuten.spliteasy.presentation.group_details.component
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.mammuten.spliteasy.domain.util.order.BillOrder
//import com.mammuten.spliteasy.domain.util.order.OrderType
//import com.mammuten.spliteasy.presentation.components.DefaultRadioButton
//
//@Composable
//fun BillOrderSection(
//    modifier: Modifier = Modifier,
//    billOrder: BillOrder = BillOrder.Date(OrderType.Descending),
//    onOrderChange: (BillOrder) -> Unit
//) {
//    Column(
//        modifier = modifier,
//        content = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                content = {
//                    DefaultRadioButton(
//                        text = "Name",
//                        selected = billOrder is BillOrder.Name,
//                        onSelect = { onOrderChange(BillOrder.Name(billOrder.orderType)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Date",
//                        selected = billOrder is BillOrder.Date,
//                        onSelect = { onOrderChange(BillOrder.Date(billOrder.orderType)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Amount",
//                        selected = billOrder is BillOrder.Amount,
//                        onSelect = { onOrderChange(BillOrder.Amount(billOrder.orderType)) }
//                    )
//                }
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                content = {
//                    DefaultRadioButton(
//                        text = "Ascending",
//                        selected = billOrder.orderType is OrderType.Ascending,
//                        onSelect = { onOrderChange(billOrder.copy(OrderType.Ascending)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Descending",
//                        selected = billOrder.orderType is OrderType.Descending,
//                        onSelect = { onOrderChange(billOrder.copy(OrderType.Descending)) }
//                    )
//                }
//            )
//        }
//    )
//}
//
//@Preview
//@Composable
//fun BillOrderSectionPreview() {
//    BillOrderSection(onOrderChange = {})
//}