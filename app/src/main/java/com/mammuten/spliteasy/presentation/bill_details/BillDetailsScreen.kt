package com.mammuten.spliteasy.presentation.bill_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
    val openDeleteContributionDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
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
                        Screen.AddEditContributionScreen.route + "/${state.bill?.id}"
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
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            items(
                                items = state.membersWithContributions,
                                key = { member -> member.id!! },
                                itemContent = { member ->
                                    OutlinedCard(
                                        content = {
                                            Text(
                                                modifier = Modifier.padding(8.dp),
                                                text = member.name
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
//                BillOrderSection(
//                    modifier = Modifier.fillMaxWidth(),
//                    billOrder = state.billOrder,
//                    onOrderChange = { viewModel.onEvent(GroupDetailsEvent.Order(it)) }
//                )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            items(
                                items = state.contributions,
                                key = { contribution -> contribution.memberId },
                                itemContent = { contribution ->
                                    OutlinedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surface,
                                        ),
                                        border = BorderStroke(width = 1.dp, color = Color.Black),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                navController.navigate(
                                                    Screen.AddEditContributionScreen.route +
                                                            "/${state.bill?.id}" +
                                                            "?memberId=${contribution.memberId}"
                                                )
                                            },
                                        content = {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                content = {
                                                    Text(
                                                        text = "Member",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        modifier = Modifier.padding(bottom = 4.dp)
                                                    )
                                                    Text(
                                                        text = state.membersWithContributions
                                                            .firstOrNull { it.id == contribution.memberId }
                                                            ?.name ?: "N/A",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        modifier = Modifier.padding(bottom = 16.dp)
                                                    )

                                                    Text(
                                                        text = "Amount paid",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        modifier = Modifier.padding(bottom = 4.dp)
                                                    )
                                                    Text(
                                                        text = String.format(
                                                            "%.2f",
                                                            contribution.amountPaid
                                                        ),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        modifier = Modifier.padding(bottom = 16.dp)
                                                    )

                                                    Text(
                                                        text = "Amount owed",
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        modifier = Modifier.padding(bottom = 4.dp)
                                                    )
                                                    Text(
                                                        text = String.format(
                                                            "%.2f",
                                                            contribution.amountOwed
                                                        ),
                                                        style = MaterialTheme.typography.bodyMedium,
                                                    )
                                                }
                                            )
                                            IconButton(
                                                onClick = {
                                                    openDeleteContributionDialog.value = true
                                                },
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete contribution"
                                                    )
                                                }
                                            )
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

                        openDeleteContributionDialog.value -> {
                            ConfirmDismissDialog(
                                onDismissRequest = { openDeleteContributionDialog.value = false },
                                onConfirmation = {
                                    openDeleteContributionDialog.value = false
                                    onEvent(BillDetailsEvent.DeleteContribution)
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
            contributions = listOf(
                Contribution(
                    billId = 1,
                    memberId = 1,
                    amountPaid = 50.0,
                    amountOwed = 50.0
                ),
                Contribution(
                    billId = 1,
                    memberId = 2,
                    amountPaid = 50.0,
                    amountOwed = 50.0
                )
            ),
            membersWithContributions = listOf(
                Member(
                    id = 1,
                    groupId = 1,
                    name = "Member 1"
                ),
                Member(
                    id = 2,
                    groupId = 1,
                    name = "Member 2"
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
