package com.mammuten.spliteasy.presentation.add_users_to_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUsersToGroupScreen(
    navController: NavController,
    state: AddUsersToGroupViewModel.State,
    onEvent: (AddUsersToGroupEvent) -> Unit,
    eventFlow: SharedFlow<AddUsersToGroupViewModel.UiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddUsersToGroupViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is AddUsersToGroupViewModel.UiEvent.SaveUsersToGroup -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save users") },
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
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            if (state.users.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { onEvent(AddUsersToGroupEvent.SaveUsers) },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save users"
                        )
                    }
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    state.users.forEach { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(text = user.name)
                                Checkbox(
                                    checked = state.selectedUsers[user]!!,
                                    onCheckedChange = { isChecked ->
                                        onEvent(
                                            AddUsersToGroupEvent.ToggleUserSelection(
                                                user, isChecked
                                            )
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}

@Preview
@Composable
fun AddUsersToGroupScreenPreview() {
    val users = listOf(
        User(
            id = 1,
            name = "User 1",
        ),
        User(
            id = 2,
            name = "User 2",
        ),
    )
    AddUsersToGroupScreen(
        navController = rememberNavController(),
        state = AddUsersToGroupViewModel.State(
            users = users,
            selectedUsers = mapOf(
                users[0] to false,
                users[1] to true
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}