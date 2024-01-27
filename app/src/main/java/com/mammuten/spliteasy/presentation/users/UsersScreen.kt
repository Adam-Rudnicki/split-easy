package com.mammuten.spliteasy.presentation.users

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.presentation.components.ConfirmDismissDialog
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navController: NavController,
    state: UsersState,
    onEvent: (UsersEvent) -> Unit,
    eventFlow: SharedFlow<UsersViewModel.UiEvent>
) {
    val openDeleteUserDialog = remember { mutableStateOf<User?>(null) }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UsersViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHostState.showSnackbar(message = event.message)
                }

                is UsersViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Users") },
                actions = {
                    IconButton(
                        onClick = { onEvent(UsersEvent.NavigateToAddEditUserScreen()) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.PersonAdd,
                                contentDescription = "Edit group"
                            )
                        }
                    )
                },
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
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        color = Color.Black
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            items(
                                items = state.users,
                                key = { user -> user.id!! },
                                itemContent = { user ->
                                    OutlinedCard(
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                        border = BorderStroke(width = 1.dp, color = Color.Black),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp)
                                            .clickable {
                                                onEvent(UsersEvent.NavigateToAddEditUserScreen(user.id))
                                            },
                                        content = {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                content = {
                                                    // User Name, Surname, and Nick in one row
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(end = 8.dp),
                                                        content = {
                                                            Row(
                                                                modifier = Modifier.fillMaxWidth(),
                                                            ) {
                                                                Text(
                                                                    modifier = Modifier.weight(0.7f),
                                                                    text = user.name,
                                                                    style = MaterialTheme.typography.bodyLarge,
                                                                    maxLines = 1,
                                                                    overflow = TextOverflow.Ellipsis
                                                                )
                                                                user.surname?.let {
                                                                    Text(
                                                                        modifier = Modifier.weight(
                                                                            1f
                                                                        ),
                                                                        text = it,
                                                                        style = MaterialTheme.typography.bodyLarge,
                                                                        maxLines = 1,
                                                                        overflow = TextOverflow.Ellipsis,
                                                                    )
                                                                }
                                                                user.nick?.let {
                                                                    Text(
                                                                        modifier = Modifier.weight(
                                                                            0.8f
                                                                        ),
                                                                        text = it,
                                                                        style = MaterialTheme.typography.bodyLarge,
                                                                        maxLines = 1,
                                                                        overflow = TextOverflow.Ellipsis,
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    )
                                                    IconButton(
                                                        modifier = Modifier.weight(0.1f),
                                                        onClick = {
                                                            openDeleteUserDialog.value = user
                                                        },
                                                        content = {
                                                            Icon(
                                                                imageVector = Icons.Default.Delete,
                                                                contentDescription = "Delete"
                                                            )
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
                }
            )

            when {
                openDeleteUserDialog.value != null -> {
                    val userToDelete = openDeleteUserDialog.value!!
                    ConfirmDismissDialog(
                        onDismissRequest = { openDeleteUserDialog.value = null },
                        onConfirmation = {
                            openDeleteUserDialog.value = null
                            onEvent(UsersEvent.DeleteUser(userToDelete))
                        },
                        dialogTitle = "Delete user",
                        dialogText = "Are you sure you want to delete this user?",
                        icon = Icons.Default.Delete
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun UsersScreenPreview() {
    UsersScreen(
        navController = rememberNavController(),
        state = UsersState(
            users = listOf(
                User(
                    id = 1,
                    name = "User 1",
                    surname = "Surname 1",
                    nick = "Nick 1"
                ),
                User(
                    id = 2,
                    name = "User 2"
                )
            )
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}