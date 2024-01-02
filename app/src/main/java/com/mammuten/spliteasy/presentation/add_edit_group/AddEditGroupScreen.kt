package com.mammuten.spliteasy.presentation.add_edit_group

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.presentation.components.FormTextInput
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditGroupScreen(
    navController: NavController,
    viewModel: AddEditGroupViewModel = hiltViewModel()
) {
    val nameState = viewModel.name
    val descriptionState = viewModel.description

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditGroupViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is AddEditGroupViewModel.UiEvent.SaveGroup -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save group") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditGroupEvent.SaveGroup) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save group"
                    )
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
            ) {
                FormTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Name",
                    text = nameState.value,
                    error = nameState.error,
                    onValueChange = { viewModel.onEvent(AddEditGroupEvent.EnteredName(it)) },
                    isRequired = Group.IS_NAME_REQUIRED,
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Description",
                    text = descriptionState.value,
                    error = descriptionState.error,
                    onValueChange = { viewModel.onEvent(AddEditGroupEvent.EnteredDescription(it)) },
                    isRequired = Group.IS_DESC_REQUIRED,
                    singleLine = false
                )
            }
        }
    )
}