package com.mammuten.spliteasy.presentation.group_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.presentation.util.Screen
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is GroupDetailsViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is GroupDetailsViewModel.UiEvent.ShowSnackbarRestoreMember -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.actionLabel,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            onEvent(GroupDetailsEvent.RestoreMember)
                        }
                    }

                }

                is GroupDetailsViewModel.UiEvent.DeleteGroup -> navController.navigateUp()
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
                        onClick = { openDeleteGroupDialog.value = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete group"
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Screen.AddEditGroupScreen.route + "?groupId=${state.group?.id}"
                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit group"
                            )
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditBillScreen.route + "/${state.group?.id}") },
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
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                content = {
                                    state.group?.let {
                                        Text(
                                            text = "Name",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = it.name + "nig",
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
                                            text = "Creation Date",
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                        Text(
                                            text = it.created.toString(),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }
                            )
                        }
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate(
                                Screen.AddEditMemberScreen.route + "/${state.group?.id}"
                            )
                        },
                        content = { Text(text = "Add member") }
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        content = {
                            items(
                                items = state.members,
                                key = { member -> member.id!! },
                                itemContent = { member ->
                                    Button(
                                        onClick = {
                                            navController.navigate(
                                                Screen.AddEditMemberScreen.route +
                                                        "/${state.group?.id}" +
                                                        "?memberId=${member.id}"
                                            )
                                        },
                                        content = { Text(text = member.name) }
                                    )
                                    IconButton(
                                        onClick = { openDeleteMemberDialog.value = member },
                                        modifier = Modifier.padding(top = 8.dp),
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete member"
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
                                                navController.navigate(
                                                    Screen.BillDetailsScreen.route + "/${bill.id}"
                                                )
                                            },
                                        content = {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                content = {
                                                    Text(
                                                        text = bill.name,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                    bill.date?.let {
                                                        Text(
                                                            text = it.toString(),
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis,
                                                        )
                                                    }
                                                    bill.amount?.let {
                                                        Text(
                                                            text = String.format("%.2f", it),
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
