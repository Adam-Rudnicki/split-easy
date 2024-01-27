package com.mammuten.spliteasy.presentation.group_members

import MemberOrderSection
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.presentation.util.Screen
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is GroupMembersViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }

                is GroupMembersViewModel.UiEvent.ShowSnackbarRestoreMember -> {
                    val result = snackbarHostState.showSnackbar(
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
                        onClick = { onEvent(GroupMembersEvent.NavigateToAddUsersScreen) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Add users"
                            )
                        }
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    MemberOrderSection(
                        modifier = Modifier.fillMaxWidth(),
                        memberOrder = state.memberOrder,
                        onOrderChange = { onEvent(GroupMembersEvent.MembersOrder(it)) }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            items(
                                items = state.members,
                                key = { member -> member.id!! },
                                itemContent = { member ->
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
                                                    Screen.AddEditMemberScreen.route +
                                                            "/${member.groupId}" +
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
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(GroupMembersEvent.NavigateToAddMemberScreen) },
                        content = { Text(text = "Add member") }
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
