package com.mammuten.spliteasy.presentation.add_edit_member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMemberScreen(
    navController: NavController,
    nameState: TextFieldState,
    state: AddEditMemberViewModel.State,
    onEvent: (AddEditMemberEvent) -> Unit,
    eventFlow: SharedFlow<AddEditMemberViewModel.UiEvent>
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddEditMemberViewModel.UiEvent.ShowSnackbar ->
                    snackBarHostState.showSnackbar(message = event.message)

                is AddEditMemberViewModel.UiEvent.SaveMember -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save member") },
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
                        onClick = { onEvent(AddEditMemberEvent.SaveMember) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save member"
                            )
                        }
                    )
                }
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
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Name",
                        text = nameState.value,
                        error = nameState.error,
                        onValueChange = { onEvent(AddEditMemberEvent.EnteredName(it)) },
                        isRequired = Member.IS_NAME_REQUIRED,
                        isEnabled = state.selectedUser == null,
                    )
                    state.users.forEach { user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(text = user.name)
                                RadioButton(
                                    selected = state.selectedUser == user,
                                    onClick = { onEvent(AddEditMemberEvent.ToggleUserSelection(user)) }
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
fun AddEditMemberScreenPreview() {
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
    AddEditMemberScreen(
        navController = rememberNavController(),
        nameState = remember {
            TextFieldState(
                value = "Name",
                error = null,
            )
        },
        state = AddEditMemberViewModel.State(
            users = users,
            selectedUser = users.first()
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}