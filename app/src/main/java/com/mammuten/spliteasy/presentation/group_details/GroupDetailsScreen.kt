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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.presentation.Screen
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailsScreen(
    navController: NavController,
    viewModel: GroupDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    val openDeleteGroupDialog = remember { mutableStateOf(false) }
    val openDeleteMemberDialog = remember { mutableStateOf(false) }
    val selectedMemberToDelete = remember { mutableStateOf<Member?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is GroupDetailsViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

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
                                        onClick = {
                                            selectedMemberToDelete.value = member
                                            openDeleteMemberDialog.value = true
                                        },
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
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
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
                }
            )
            when {
                openDeleteGroupDialog.value -> {
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteGroupDialog.value = false },
                        onConfirmation = {
                            openDeleteGroupDialog.value = false
                            viewModel.onEvent(GroupDetailsEvent.DeleteGroup)
                        },
                        dialogTitle = "Delete group",
                        dialogText = "Are you sure you want to delete this group?",
                        icon = Icons.Default.Delete
                    )
                }

                openDeleteMemberDialog.value -> {
                    selectedMemberToDelete.value?.let { member ->
                        ConfirmDismissDialog(
                            onDismissRequest = {
                                selectedMemberToDelete.value = null
                                openDeleteMemberDialog.value = false
                            },
                            onConfirmation = {
                                openDeleteMemberDialog.value = false
                                viewModel.onEvent(GroupDetailsEvent.DeleteMember(member))
                                selectedMemberToDelete.value = null
                            },
                            dialogTitle = "Delete member",
                            dialogText = "Are you sure you want to delete this member?",
                            icon = Icons.Default.Delete
                        )
                    }
                }
            }
        }
    )
}
