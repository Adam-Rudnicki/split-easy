//import androidx.compose.foundation.layout.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.mammuten.spliteasy.domain.util.order.MemberOrder
//import com.mammuten.spliteasy.domain.util.order.OrderType
//import com.mammuten.spliteasy.presentation.components.DefaultRadioButton
//
//@Composable
//fun MemberOrderSection(
//    modifier: Modifier = Modifier,
//    memberOrder: MemberOrder = MemberOrder.Name(OrderType.Ascending),
//    onOrderChange: (MemberOrder) -> Unit
//) {
//    Column(
//        modifier = modifier,
//        content = {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                content = {
//                    DefaultRadioButton(
//                        text = "Ascending",
//                        selected = memberOrder.orderType is OrderType.Ascending,
//                        onSelect = { onOrderChange(memberOrder.copy(OrderType.Ascending)) }
//                    )
//                    DefaultRadioButton(
//                        text = "Descending",
//                        selected = memberOrder.orderType is OrderType.Descending,
//                        onSelect = { onOrderChange(memberOrder.copy(OrderType.Descending)) }
//                    )
//                }
//            )
//        }
//    )
//}
//
//@Preview
//@Composable
//fun MemberOrderSectionPreview() {
//    MemberOrderSection(onOrderChange = {})
//}