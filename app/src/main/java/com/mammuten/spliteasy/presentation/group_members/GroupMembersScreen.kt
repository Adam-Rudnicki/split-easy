package com.mammuten.spliteasy.presentation.group_members

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.MemberOrder
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupMembersScreen(
    navController: NavController,
    state: GroupMembersState,
    onEvent: (GroupMembersEvent) -> Unit,
    eventFlow: SharedFlow<GroupMembersViewModel.UiEvent>,
) {
    val openDeleteMemberDialog = remember { mutableStateOf<Member?>(null) }
    val snackBarHostState = remember { SnackbarHostState() }
    var isContextSortMenuVisible by remember { mutableStateOf(false) }
    var isContextAddMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is GroupMembersViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                is GroupMembersViewModel.UiEvent.ShowSnackbarRestoreMember -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(GroupMembersEvent.RestoreMember)
                    }
                }

                is GroupMembersViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Group Members") },
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
                        onClick = { isContextSortMenuVisible = !isContextSortMenuVisible },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Sort,
                                contentDescription = "Sort menu"
                            )
                            DropdownMenu(
                                expanded = isContextSortMenuVisible,
                                onDismissRequest = { isContextSortMenuVisible = false },
                                modifier = Modifier.padding(4.dp),
                                content = {
                                    DropdownMenuItem(
                                        onClick = {
                                            onEvent(GroupMembersEvent.MembersOrder(MemberOrder.NameAsc))
                                            isContextSortMenuVisible = false
                                        },
                                        text = { Text(text = "Name asc") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            onEvent(GroupMembersEvent.MembersOrder(MemberOrder.NameDesc))
                                            isContextSortMenuVisible = false
                                        },
                                        text = { Text(text = "Name desc") }
                                    )
                                }
                            )
                        }
                    )
                    IconButton(
                        onClick = { isContextAddMenuVisible = !isContextAddMenuVisible },
                        content = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = "Add users"
                            )
                            DropdownMenu(
                                expanded = isContextAddMenuVisible,
                                onDismissRequest = { isContextAddMenuVisible = false },
                                modifier = Modifier.padding(4.dp),
                                content = {
                                    DropdownMenuItem(
                                        onClick = {
                                            onEvent(GroupMembersEvent.NavigateToAddUsersScreen)
                                            isContextAddMenuVisible = false
                                        },
                                        text = { Text(text = "Add user") }
                                    )
                                    DropdownMenuItem(
                                        onClick = {
                                            onEvent(GroupMembersEvent.NavigateToAddEditMemberScreen())
                                            isContextAddMenuVisible = false
                                        },
                                        text = { Text(text = "Add member") }
                                    )
                                }
                            )
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    items(
                        items = state.members,
                        key = { member -> member.id!! },
                        itemContent = { member ->
                            MyOutlineCard(
                                color = member.userId?.let { MaterialTheme.colorScheme.surface }
                                    ?: MaterialTheme.colorScheme.background,
                                member = member,
                                onDelete = { openDeleteMemberDialog.value = member },
                                onClick = {
                                    onEvent(
                                        GroupMembersEvent.NavigateToAddEditMemberScreen(member.id)
                                    )
                                }
                            )
                        }
                    )
                }
            )

            when {
                openDeleteMemberDialog.value != null -> {
                    val memberToDelete = openDeleteMemberDialog.value!!
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteMemberDialog.value = null },
                        onConfirmation = {
                            openDeleteMemberDialog.value = null
                            onEvent(GroupMembersEvent.DeleteMember(memberToDelete))
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

@Composable
fun MyOutlineCard(
    color: Color,
    member: Member,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = color),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = member.name,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, top = 12.dp)
                    )
                    IconButton(
                        onClick = { onDelete() },
                        modifier = Modifier.padding(end = 8.dp),
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
}

@Preview
@Composable
fun GroupMembersScreenPreview() {
    GroupMembersScreen(
        navController = rememberNavController(),
        state = GroupMembersState(
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
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}
