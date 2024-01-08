package com.mammuten.spliteasy.presentation.add_edit_bill

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.MyDatePicker
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBillScreen(
    navController: NavController,
    viewModel: AddEditBillViewModel = hiltViewModel()
) {
    val nameState = viewModel.name
    val descriptionState = viewModel.description
    val amountState = viewModel.amount
    val dateState = viewModel.date

    val snackbarHostState = remember { SnackbarHostState() }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditBillViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditBillEvent.SaveBill) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save bill"
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
                        onValueChange = { viewModel.onEvent(AddEditBillEvent.EnteredName(it)) },
                        isRequired = Bill.IS_NAME_REQUIRED,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Description",
                        text = descriptionState.value,
                        error = descriptionState.error,
                        onValueChange = { viewModel.onEvent(AddEditBillEvent.EnteredDescription(it)) },
                        isRequired = Bill.IS_DESC_REQUIRED,
                        singleLine = false
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FormTextInput(
                        modifier = Modifier.fillMaxWidth(),
                        label = "Amount",
                        text = amountState.value,
                        error = amountState.error,
                        onValueChange = { viewModel.onEvent(AddEditBillEvent.EnteredAmount(it)) },
                        isRequired = Bill.IS_AMOUNT_REQUIRED,
                        keyboardType = KeyboardType.Number
                    )
                    Text(text = "Date: ${dateState.value ?: "not set"}")
                    Button(
                        onClick = { showDatePicker = true },
                        content = { Text(text = "Choose date") }
                    )
                    Button(
                        onClick = { viewModel.onEvent(AddEditBillEvent.EnteredDate(null)) },
                        content = { Text(text = "Clear date") }
                    )
                }
            )
            if (showDatePicker) {
                MyDatePicker(
                    date = dateState.value ?: Date(),
                    onConfirm = {
                        viewModel.onEvent(AddEditBillEvent.EnteredDate(it))
                        showDatePicker = false
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    )
}