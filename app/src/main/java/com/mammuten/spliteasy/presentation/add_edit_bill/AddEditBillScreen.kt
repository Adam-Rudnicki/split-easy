package com.mammuten.spliteasy.presentation.add_edit_bill

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.MyDatePickerDialog
import com.mammuten.spliteasy.presentation.components.input_state.DateState
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBillScreen(
    navController: NavController,
    nameState: TextFieldState,
    descriptionState: TextFieldState,
    dateState: DateState,
    isSavingState: Boolean,
    onEvent: (AddEditBillEvent) -> Unit,
    eventFlow: SharedFlow<AddEditBillViewModel.UiEvent>
) {
    val snackBarHostState = remember { SnackbarHostState() }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is AddEditBillViewModel.UiEvent.ShowSnackbar ->
                    snackBarHostState.showSnackbar(message = event.message)

                is AddEditBillViewModel.UiEvent.SaveBill -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Save bill") },
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    IconButton(
                        onClick = { onEvent(AddEditBillEvent.SaveBill) },
                        enabled = !isSavingState,
                        content = {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save bill"
                            )
                        }
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
                        onValueChange = { onEvent(AddEditBillEvent.EnteredName(it)) },
                        isRequired = Bill.IS_NAME_REQUIRED,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Description",
                        text = descriptionState.value,
                        error = descriptionState.error,
                        onValueChange = { onEvent(AddEditBillEvent.EnteredDescription(it)) },
                        isRequired = Bill.IS_DESC_REQUIRED,
                        singleLine = false
                    )
                    Text(text = "Date: ${dateState.value ?: "not set"}")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        content = {
                            Button(
                                onClick = { showDatePicker = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp),
                                content = { Text(text = "Choose date") }
                            )
                            Button(
                                onClick = { onEvent(AddEditBillEvent.EnteredDate(null)) },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp),
                                content = { Text(text = "Clear date") }
                            )
                        }
                    )
                }
            )

            if (showDatePicker) {
                MyDatePickerDialog(
                    date = dateState.value ?: Date(),
                    onConfirm = {
                        onEvent(AddEditBillEvent.EnteredDate(it))
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    )
}

@Preview
@Composable
fun AddEditBillScreenPreview() {
    AddEditBillScreen(
        navController = rememberNavController(),
        nameState = TextFieldState(),
        descriptionState = TextFieldState(),
        dateState = DateState(),
        isSavingState = true,
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}