package com.mammuten.spliteasy.presentation.group_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.presentation.util.Screen
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    navController: NavController,
    state: GroupDetailsState,
    onEvent: (GroupDetailsEvent) -> Unit,
    eventFlow: SharedFlow<GroupDetailsViewModel.UiEvent>,
) {
    val openDeleteGroupDialog = remember { mutableStateOf(false) }
    val openDeleteMemberDialog = remember { mutableStateOf<Member?>(null) }
    val snackBarHostState = remember { SnackbarHostState() }
    val expandedCard = remember { mutableStateOf(false) }
    var isContextMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is GroupDetailsViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                is GroupDetailsViewModel.UiEvent.ShowSnackbarRestoreMember -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(GroupDetailsEvent.RestoreMember)
                    }
                }

                is GroupDetailsViewModel.UiEvent.DeleteGroup -> navController.navigateUp()

                is GroupDetailsViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Group Details") },
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
                    IconButton(onClick = { isContextMenuVisible = !isContextMenuVisible }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                        DropdownMenu(
                            expanded = isContextMenuVisible,
                            onDismissRequest = { isContextMenuVisible = false },
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.NameAsc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Name asc") })

                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.NameDesc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Name desc") })

                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.DateAsc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Date asc") })

                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.DateDesc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Date desc") })

                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.AmountAsc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Amount asc") })

                            DropdownMenuItem(
                                onClick = {
                                    onEvent(GroupDetailsEvent.BillsOrder(BillOrder.AmountDesc))
                                    isContextMenuVisible = false
                                },
                                text = { Text(text = "Amount desc") })
                        }
                    }
                    IconButton(
                        onClick = { openDeleteGroupDialog.value = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete group"
                            )
                        }
                    )
                    IconButton(
                        onClick = { onEvent(GroupDetailsEvent.NavigateToAddEditGroupScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit group"
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.GroupMembersScreen.route + "/${state.group?.id}")
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Edit group"
                            )
                        }
                    )

                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(GroupDetailsEvent.NavigateToAddEditBillScreen) },
                content = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add bill") }
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedCard.value = !expandedCard.value
                            },
                        content = {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                content = {
                                    // Warunkowo renderuj tylko nazwę lub pełne informacje w zależności od stanu expandedCard
                                    if (!expandedCard.value) {
                                        // Tylko nazwa
                                        state.group?.let {
                                            Text(
                                                text = "Name",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = it.name,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                        }
                                    } else {
                                        // Pełne informacje
                                        state.group?.let {
                                            Text(
                                                text = "Name",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = it.name,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )

                                            Text(
                                                text = "Description",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = it.description ?: "N/A",
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )

                                            Text(
                                                text = "Creation Date",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Text(
                                                text = it.created.toString(),
                                                style = MaterialTheme.typography.bodyLarge,
                                            )
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
                            items(
                                items = state.bills,
                                key = { bill -> bill.id!! },
                                itemContent = { bill ->
                                    OutlinedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                        border = BorderStroke(width = 1.dp, color = Color.Black),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                onEvent(
                                                    GroupDetailsEvent.NavigateToBillDetailsScreen(
                                                        bill.id!!
                                                    )
                                                )
                                            },
                                        content = {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                content = {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier.fillMaxWidth(),
                                                        content = {
                                                            Text(
                                                                text = bill.name,
                                                                modifier = Modifier
                                                                    .weight(1f),
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
                                                            bill.amount?.let {
                                                                Text(
                                                                    textAlign = TextAlign.End,
                                                                    text = String.format(
                                                                        "%.2f",
                                                                        it
                                                                    ),
                                                                    modifier = Modifier.weight(1f),
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    maxLines = 1,
                                                                    overflow = TextOverflow.Ellipsis,
                                                                )
                                                            }
                                                        }
                                                    )
                                                    bill.date?.let {
                                                        Text(
                                                            text = it.toString(),
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                        )
                                                    }
                                                    bill.description?.let {
                                                        Text(
                                                            text = it,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            maxLines = 3,
                                                            overflow = TextOverflow.Ellipsis,
                                                        )
                                                    }
                                                }
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
                openDeleteGroupDialog.value -> {
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteGroupDialog.value = false },
                        onConfirmation = {
                            openDeleteGroupDialog.value = false
                            onEvent(GroupDetailsEvent.DeleteGroup)
                        },
                        dialogTitle = "Delete group",
                        dialogText = "Are you sure you want to delete this group?",
                        icon = Icons.Default.Delete
                    )
                }

                openDeleteMemberDialog.value != null -> {
                    val memberToDelete = openDeleteMemberDialog.value!!
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteMemberDialog.value = null },
                        onConfirmation = {
                            openDeleteMemberDialog.value = null
                            onEvent(GroupDetailsEvent.DeleteMember(memberToDelete))
                        },
                        dialogTitle = "Delete member",
                        dialogText = "Are you sure you want to delete this member?",
                        icon = Icons.Default.Delete
                    )
                }
            }
        }
    )
}


@Preview
@Composable
fun GroupDetailsScreenPreview() {
    GroupDetailsScreen(
        navController = rememberNavController(),
        state = GroupDetailsState(
            group = Group(
                id = 1,
                name = "Group 1",
                description = "Description 1",
                created = Date()
            ),
            members = listOf(
                Member(
                    id = 1,
                    name = "Member 1",
                    groupId = 1
                ),
                Member(
                    id = 2,
                    name = "Member 2",
                    groupId = 1
                )
            ),
            bills = listOf(
                Bill(
                    id = 1,
                    name = "Bill 1",
                    description = "Description 1",
                    amount = 100.0,
                    groupId = 1,
                    date = Date()
                ),
                Bill(
                    id = 2,
                    name = "Bill 2",
                    description = "Description 2",
                    amount = 200.0,
                    groupId = 1,
                    date = Date()
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
