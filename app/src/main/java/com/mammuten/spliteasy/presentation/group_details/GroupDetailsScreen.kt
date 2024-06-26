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
import androidx.compose.material.icons.filled.SwapVerticalCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.presentation.util.Screen
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import com.mammuten.spliteasy.presentation.components.MyDropdownMenu
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
    var openDeleteGroupDialog by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    var isCardExpanded by remember { mutableStateOf(false) }
    var isContextMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is GroupDetailsViewModel.UiEvent.ShowSnackbar ->
                    snackBarHostState.showSnackbar(message = event.message)

                is GroupDetailsViewModel.UiEvent.DeleteGroup -> navController.navigateUp()
                is GroupDetailsViewModel.UiEvent.Navigate -> navController.navigate(event.route)
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
                    IconButton(
                        onClick = { openDeleteGroupDialog = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete group"
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
                                    "Name asc" to { onEvent(GroupDetailsEvent.BillsOrder(BillOrder.NameAsc)) },
                                    "Name desc" to { onEvent(GroupDetailsEvent.BillsOrder(BillOrder.NameDesc)) },
                                    "Date asc" to { onEvent(GroupDetailsEvent.BillsOrder(BillOrder.DateAsc)) },
                                    "Date desc" to { onEvent(GroupDetailsEvent.BillsOrder(BillOrder.DateDesc)) }
                                )
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
                                contentDescription = "Group members"
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
                            .clickable { isCardExpanded = !isCardExpanded },
                        content = {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                content = {
                                    state.group?.let {
                                        Text(
                                            text = "Name: ${it.name}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        if (isCardExpanded) {
                                            it.description?.let { desc ->
                                                Text(
                                                    text = "Description: $desc",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    modifier = Modifier.padding(bottom = 4.dp)
                                                )
                                            }
                                            Text(
                                                text = "Creation Date: ${it.created}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                modifier = Modifier.padding(bottom = 4.dp)
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
                                                                modifier = Modifier.weight(1f),
                                                                style = MaterialTheme.typography.bodyLarge,
                                                                maxLines = 1,
                                                                overflow = TextOverflow.Ellipsis
                                                            )
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
                openDeleteGroupDialog -> {
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteGroupDialog = false },
                        onConfirmation = {
                            openDeleteGroupDialog = false
                            onEvent(GroupDetailsEvent.DeleteGroup)
                        },
                        dialogTitle = "Delete group",
                        dialogText = "Are you sure you want to delete this group?",
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
            bills = listOf(
                Bill(
                    id = 1,
                    name = "Bill 1",
                    description = "Description 1",
                    groupId = 1,
                    date = Date()
                ),
                Bill(
                    id = 2,
                    name = "Bill 2",
                    description = "Description 2",
                    groupId = 1,
                    date = Date()
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
