package com.mammuten.spliteasy.presentation.add_edit_user

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditUserScreen(
    navController: NavController,
    nameState: TextFieldState,
    surnameState: TextFieldState,
    nickState: TextFieldState,
    onEvent: (AddEditUserEvent) -> Unit,
    eventFlow: SharedFlow<AddEditUserViewModel.UiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddEditUserViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is AddEditUserViewModel.UiEvent.SaveUser -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save user") },
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
            FloatingActionButton(
                onClick = { onEvent(AddEditUserEvent.SaveUser) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save user"
                    )
                }
            )
        },
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
                        onValueChange = { onEvent(AddEditUserEvent.EnteredName(it)) },
                        isRequired = User.IS_NAME_REQUIRED,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Surname",
                        text = surnameState.value,
                        error = surnameState.error,
                        onValueChange = { onEvent(AddEditUserEvent.EnteredSurname(it)) },
                        isRequired = User.IS_SURNAME_REQUIRED,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Nick",
                        text = nickState.value,
                        error = nickState.error,
                        onValueChange = { onEvent(AddEditUserEvent.EnteredNick(it)) },
                        isRequired = User.IS_NICK_REQUIRED,
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun AddEditUserScreenPreview() {
    AddEditUserScreen(
        navController = rememberNavController(),
        nameState = TextFieldState(
            value = "gr",
            error = InvalidInputError.TooShortText(3)
        ),
        surnameState = TextFieldState(),
        nickState = TextFieldState(),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}