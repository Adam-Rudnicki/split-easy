package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.SwapVerticalCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.presentation.bill_details.component.TableHeader
import com.mammuten.spliteasy.presentation.bill_details.component.TableRow
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import com.mammuten.spliteasy.presentation.components.MyDropdownMenu
import com.mammuten.spliteasy.presentation.util.PriceUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailsScreen(
    navController: NavController,
    state: BillDetailsState,
    onEvent: (BillDetailsEvent) -> Unit,
    eventFlow: SharedFlow<BillDetailsViewModel.UiEvent>,
) {
    var openDeleteBillDialog by remember { mutableStateOf(false) }
    var openDeleteContributionDialog by remember { mutableStateOf<Contribution?>(null) }
    val snackBarHostState = remember { SnackbarHostState() }
    var isCardExpanded by remember { mutableStateOf(false) }
    var isContextMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is BillDetailsViewModel.UiEvent.ShowSnackbar ->
                    snackBarHostState.showSnackbar(message = event.message)

                is BillDetailsViewModel.UiEvent.ShowSnackbarRestoreContribution -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(BillDetailsEvent.RestoreContribution)
                    }
                }

                is BillDetailsViewModel.UiEvent.DeleteBill -> navController.navigateUp()
                is BillDetailsViewModel.UiEvent.Navigate -> navController.navigate(event.route)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Bill Details") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { openDeleteBillDialog = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete bill"
                            )
                        }
                    )
                    IconButton(
                        onClick = { isContextMenuVisible = !isContextMenuVisible },
                        content = {
                            Icon(
                                imageVector = Icons.Default.SwapVerticalCircle,
                                contentDescription = "Sort"
                            )
                            MyDropdownMenu(
                                expanded = isContextMenuVisible,
                                onDismissRequest = { isContextMenuVisible = false },
                                items = listOf(
                                    "Amount Paid Asc" to {
                                        onEvent(
                                            BillDetailsEvent.ContributionsOrder(
                                                ContributionOrder.AmountPaidAsc
                                            )
                                        )
                                    },
                                    "Amount Paid Desc" to {
                                        onEvent(
                                            BillDetailsEvent.ContributionsOrder(
                                                ContributionOrder.AmountPaidDesc
                                            )
                                        )
                                    },
                                    "Amount Owed Asc" to {
                                        onEvent(
                                            BillDetailsEvent.ContributionsOrder(
                                                ContributionOrder.AmountOwedAsc
                                            )
                                        )
                                    },
                                    "Amount Owed Desc" to {
                                        onEvent(
                                            BillDetailsEvent.ContributionsOrder(
                                                ContributionOrder.AmountOwedDesc
                                            )
                                        )
                                    }
                                )
                            )
                        }
                    )
                    IconButton(
                        onClick = { onEvent(BillDetailsEvent.NavigateToAddEditBillScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit bill"
                            )
                        }
                    )
                    IconButton(
                        onClick = { onEvent(BillDetailsEvent.NavigateToManageContributionsScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = "Manage contributions"
                            )
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isCardExpanded = !isCardExpanded },
                        content = {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                content = {
                                    state.bill?.let {
                                        Text(
                                            text = "Name: ${it.name}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 2.dp)
                                        )
                                        if (isCardExpanded) {
                                            it.description?.let { desc ->
                                                Text(
                                                    text = "Description: $desc",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    modifier = Modifier.padding(bottom = 2.dp)
                                                )
                                            }
                                            it.date?.let { date ->
                                                Text(
                                                    text = "Date: $date",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    modifier = Modifier.padding(bottom = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        color = Color.Black
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            item(
                                content = {
                                    TableHeader(
                                        memberHeaderText = "Member",
                                        amountPaidHeaderText = "Paid",
                                        amountOwedHeaderText = "Owed",
                                        onCalculateClick = { onEvent(BillDetailsEvent.Check) }
                                    )
                                }
                            )
                            items(
                                items = state.membersAndContributions,
                                key = { (member, _) -> member.id!! },
                                itemContent = { (member, contribution) ->
                                    TableRow(
                                        memberName = member.name,
                                        amountPaid = PriceUtil.scaleAndDivide(contribution.amountPaid),
                                        amountOwed = PriceUtil.scaleAndDivide(contribution.amountOwed),
                                        onDeleteClick = {
                                            openDeleteContributionDialog = contribution
                                        }
                                    )
                                }
                            )
                        }
                    )

                    Text(
                        text = "Sum of amount paid: ${PriceUtil.scaleAndDivide(state.sumOfAmountPaid)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Sum of amount owed: ${PriceUtil.scaleAndDivide(state.sumOfAmountOwed)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "Difference: ${PriceUtil.scaleAndDivide(state.absoluteDifference)}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )

                    when {
                        openDeleteBillDialog -> {
                            ConfirmDismissDialog(
                                onDismissRequest = { openDeleteBillDialog = false },
                                onConfirmation = {
                                    openDeleteBillDialog = false
                                    onEvent(BillDetailsEvent.DeleteBill)
                                },
                                dialogTitle = "Delete bill",
                                dialogText = "Are you sure you want to delete this bill?",
                                icon = Icons.Default.Delete
                            )
                        }

                        openDeleteContributionDialog != null -> {
                            val contributionToDelete = openDeleteContributionDialog!!
                            ConfirmDismissDialog(
                                onDismissRequest = { openDeleteContributionDialog = null },
                                onConfirmation = {
                                    openDeleteContributionDialog = null
                                    onEvent(BillDetailsEvent.DeleteContribution(contributionToDelete))
                                },
                                dialogTitle = "Delete contribution",
                                dialogText = "Are you sure you want to delete this contribution?",
                                icon = Icons.Default.Delete
                            )
                        }
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun BillDetailsScreenPreview() {
    BillDetailsScreen(
        navController = rememberNavController(),
        state = BillDetailsState(
            bill = Bill(
                id = 1,
                groupId = 1,
                name = "Bill 1",
                description = "Bill 1 description",
                date = Date()
            ),
            membersAndContributions = listOf(
                Member(
                    id = 1,
                    groupId = 1,
                    name = "Member 1"
                ) to Contribution(
                    billId = 1,
                    memberId = 1,
                    amountPaid = 5500,
                    amountOwed = 5555
                ),
                Member(
                    id = 2,
                    groupId = 1,
                    name = "Member 2"
                ) to Contribution(
                    billId = 1,
                    memberId = 2,
                    amountPaid = 5000,
                    amountOwed = 5000
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
