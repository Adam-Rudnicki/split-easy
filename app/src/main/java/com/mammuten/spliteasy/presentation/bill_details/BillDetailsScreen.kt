package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.presentation.bill_details.component.ContributionOrderSection
import com.mammuten.spliteasy.presentation.bill_details.component.TableHeader
import com.mammuten.spliteasy.presentation.bill_details.component.TableRow
import com.mammuten.spliteasy.presentation.util.Screen
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
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
    val openDeleteBillDialog = remember { mutableStateOf(false) }
    val openDeleteContributionDialog = remember { mutableStateOf<Contribution?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is BillDetailsViewModel.UiEvent.ShowSnackbarRestoreContribution -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(BillDetailsEvent.RestoreContribution)
                    }
                }

                is BillDetailsViewModel.UiEvent.DeleteBill -> navController.navigateUp()
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
                        onClick = { openDeleteBillDialog.value = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete bill"
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Screen.AddEditBillScreen.route +
                                        "/${state.bill?.groupId}" +
                                        "?billId=${state.bill?.id}"
                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit bill"
                            )
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.AddEditBillScreen.route + "/${state.bill?.groupId}"
                    )
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add contribution"
                    )
                }
            )
        },
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
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                content = {
                                    state.bill?.let {
                                        Text(
                                            text = "Name",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = it.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )

                                        Text(
                                            text = "Description",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = it.description ?: "N/A",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )

                                        Text(
                                            text = "Amount",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = it.amount?.let { amount ->
                                                String.format("%.2f", amount)
                                            } ?: "N/A",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )

                                        Text(
                                            text = "Date",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = "${it.date ?: "N/A"} ",
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(bottom = 16.dp)
                                        )
                                    }
                                }
                            )
                        }
                    )
                    ContributionOrderSection(
                        modifier = Modifier.fillMaxWidth(),
                        contributionOrder = state.contributionOrder,
                        onOrderChange = { onEvent(BillDetailsEvent.ContributionsOrder(it)) }
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate(
                                Screen.ManageContributionsScreen.route +
                                        "/${state.bill?.groupId}" +
                                        "/${state.bill?.id}"
                            )
                        },
                        content = { Text(text = "Manage contributions") }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            item(
                                content = {
                                    TableHeader(
                                        memberHeaderText = "Member",
                                        amountPaidHeaderText = "Amount Paid",
                                        amountOwedHeaderText = "Amount Owed"
                                    )
                                }
                            )
                            items(
                                items = state.membersAndContributions,
                                key = { (member, _) -> member.id!! },
                                itemContent = { (member, contribution) ->
                                    TableRow(
                                        memberName = member.name,
                                        amountPaid = contribution.amountPaid.toString(),
                                        amountOwed = contribution.amountOwed.toString(),
                                        onDeleteClick = {
                                            openDeleteContributionDialog.value = contribution
                                        }
                                    )
                                }
                            )
                        }
                    )

                    when {
                        openDeleteBillDialog.value -> {
                            ConfirmDismissDialog(
                                onDismissRequest = { openDeleteBillDialog.value = false },
                                onConfirmation = {
                                    openDeleteBillDialog.value = false
                                    onEvent(BillDetailsEvent.DeleteBill)
                                },
                                dialogTitle = "Delete bill",
                                dialogText = "Are you sure you want to delete this bill?",
                                icon = Icons.Default.Delete
                            )
                        }

                        openDeleteContributionDialog.value != null -> {
                            val contributionToDelete = openDeleteContributionDialog.value!!
                            ConfirmDismissDialog(
                                onDismissRequest = { openDeleteContributionDialog.value = null },
                                onConfirmation = {
                                    openDeleteContributionDialog.value = null
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
                amount = 100.0,
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
                    amountPaid = 50.0,
                    amountOwed = 50.0
                ),
                Member(
                    id = 2,
                    groupId = 1,
                    name = "Member 2"
                ) to Contribution(
                    billId = 1,
                    memberId = 2,
                    amountPaid = 50.0,
                    amountOwed = 50.0
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
