package com.mammuten.spliteasy.presentation.add_edit_contribution

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

// TODO
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditContributionScreen(
    navController: NavController,
    memberIdState: Int,
    amountPaidState: TextFieldState,
    amountOwedState: TextFieldState,
    onEvent: (AddEditContributionEvent) -> Unit,
    eventFlow: SharedFlow<AddEditContributionViewModel.UiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddEditContributionViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is AddEditContributionViewModel.UiEvent.SaveContribution -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save contribution") },
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
                onClick = { onEvent(AddEditContributionEvent.SaveContribution) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save contribution"
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
                        label = "Amount paid",
                        text = amountPaidState.value,
                        error = amountPaidState.error,
                        onValueChange = { onEvent(AddEditContributionEvent.EnteredAmountPaid(it)) },
                        isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Amount owed",
                        text = amountOwedState.value,
                        error = amountOwedState.error,
                        onValueChange = { onEvent(AddEditContributionEvent.EnteredAmountOwed(it)) },
                        isRequired = Contribution.IS_AMOUNT_REQUIRED,
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun AddEditBillContributionPreview() {
    AddEditContributionScreen(
        navController = rememberNavController(),
        memberIdState = 1,
        amountPaidState = TextFieldState(),
        amountOwedState = TextFieldState(),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}