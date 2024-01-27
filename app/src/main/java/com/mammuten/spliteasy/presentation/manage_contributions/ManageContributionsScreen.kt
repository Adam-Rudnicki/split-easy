package com.mammuten.spliteasy.presentation.manage_contributions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.presentation.components.FormTextInput
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageContributionsScreen(
    navController: NavController,
    state: List<ManageContributionsViewModel.MemberState>,
    onEvent: (ManageContributionsEvent) -> Unit,
    eventFlow: SharedFlow<ManageContributionsViewModel.UiEvent>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is ManageContributionsViewModel.UiEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(message = event.message)

                is ManageContributionsViewModel.UiEvent.SaveContributions -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Manage contribution") },
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
                        onClick = { onEvent(ManageContributionsEvent.SaveContributions) },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save contribution"
                            )
                        }
                    )
                }
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Text(
                                modifier = Modifier
                                    .weight(0.6f),
                                text = "Member",
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Amount Paid",
                                textAlign = TextAlign.Center
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Amount Owed",
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        color = Color.Black
                    )
                    state.forEach { memberState ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                Text(
                                    modifier = Modifier
                                        .weight(0.6f),
                                    text = memberState.member.name,
                                    textAlign = TextAlign.Center
                                )
                                FormTextInput(
                                    modifier = Modifier.weight(1f),
                                    text = memberState.amountPaidState.value,
                                    error = memberState.amountPaidState.error,
                                    onValueChange = {
                                        onEvent(
                                            ManageContributionsEvent.EnteredAmountPaid(
                                                memberState.member.id!!, it
                                            )
                                        )
                                    },
                                    isRequired = false,
                                    keyboardType = KeyboardType.Decimal,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                FormTextInput(
                                    modifier = Modifier.weight(1f),
                                    text = memberState.amountOwedState.value,
                                    error = memberState.amountOwedState.error,
                                    onValueChange = {
                                        onEvent(
                                            ManageContributionsEvent.EnteredAmountOwed(
                                                memberState.member.id!!, it
                                            )
                                        )
                                    },
                                    isRequired = false,
                                    keyboardType = KeyboardType.Decimal,
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
fun AddEditBillContributionPreview() {
    ManageContributionsScreen(
        navController = rememberNavController(),
        state = listOf(
            ManageContributionsViewModel.MemberState(
                member = Member(
                    id = 1,
                    groupId = 1,
                    name = "John"
                ),
                amountPaidState = TextFieldState(value = "10.0"),
                amountOwedState = TextFieldState(value = "10.0")
            ),
            ManageContributionsViewModel.MemberState(
                member = Member(
                    id = 2,
                    groupId = 1,
                    name = "Alex"
                ),
                amountPaidState = TextFieldState(value = "10.0"),
                amountOwedState = TextFieldState(value = "")
            ),
            ManageContributionsViewModel.MemberState(
                member = Member(
                    id = 3,
                    groupId = 1,
                    name = "Bob"
                ),
                amountPaidState = TextFieldState(value = "0.0"),
                amountOwedState = TextFieldState(value = "0.0")
            ),
            ManageContributionsViewModel.MemberState(
                member = Member(
                    id = 4,
                    groupId = 1,
                    name = "Alice"
                ),
                amountPaidState = TextFieldState(value = ""),
                amountOwedState = TextFieldState(value = "")
            ),
        ),
        onEvent = {},
        eventFlow = MutableSharedFlow()
    )
}