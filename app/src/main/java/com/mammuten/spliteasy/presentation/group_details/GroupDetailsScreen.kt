package com.mammuten.spliteasy.presentation.group_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is GroupDetailsViewModel.UiEvent.DeleteGroup -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Group Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { openDeleteGroupDialog.value = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete group"
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(
                                Screen.AddEditGroupScreen.route + "?groupId=${state.group?.id}"
                            )
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit group")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Action to add a new bill
                },
                content = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add bill") }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
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
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
//                items(itemsList) { billItem ->
//                    // Your item UI here
//                }
                }

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
                }
            }
        }
    )
}
