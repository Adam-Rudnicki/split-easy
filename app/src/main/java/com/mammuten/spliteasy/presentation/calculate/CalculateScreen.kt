package com.mammuten.spliteasy.presentation.calculate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.algorithm.Payer
import com.mammuten.spliteasy.domain.util.algorithm.Receiver

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(
    navController: NavController,
    state: CalculateViewModel.State
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Payoffs") },
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
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Who",
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Whom",
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "How",
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                    state.payoffs.forEach { payoff ->
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            color = Color.Black
                        )
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            ),
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Text(
                                            modifier = Modifier.weight(0.5f),
                                            text = payoff.payer.name,
                                            textAlign = TextAlign.Center
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            content = {
                                                payoff.receivers.forEach { (receiver, amount) ->
                                                    Row(
                                                        verticalAlignment =
                                                        Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(1.dp),
                                                                text = receiver.name,
                                                                textAlign = TextAlign.Center
                                                            )
                                                            Text(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(1.dp),
                                                                text = String.format(
                                                                    "%.2f",
                                                                    amount.div(100.0)
                                                                ),
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    )
                                                }
                                            }
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
fun CalculateDialogPreview() {
    val members = listOf(
        Member(id = 1, groupId = 1, name = "John"),
        Member(id = 2, groupId = 1, name = "Jack"),
        Member(id = 3, groupId = 1, name = "Jill"),
        Member(id = 4, groupId = 1, name = "James"),
        Member(id = 5, groupId = 1, name = "Jenny"),
        Member(id = 6, groupId = 1, name = "Jade"),
        Member(id = 7, groupId = 1, name = "Jade")
    )

    val payoffs = listOf(
        Payer(
            payer = members[0],
            receivers = listOf(
                Receiver(receiver = members[1], amount = 1000),
                Receiver(receiver = members[2], amount = 1000),
                Receiver(receiver = members[3], amount = 1000),
                Receiver(receiver = members[4], amount = 1000),
                Receiver(receiver = members[5], amount = 1000),
                Receiver(receiver = members[6], amount = 1000)
            )
        ),
        Payer(
            payer = members[1],
            receivers = listOf(
                Receiver(receiver = members[1], amount = 1000),
                Receiver(receiver = members[2], amount = 1000),
                Receiver(receiver = members[3], amount = 1000),
                Receiver(receiver = members[4], amount = 1000),
                Receiver(receiver = members[5], amount = 1000),
                Receiver(receiver = members[6], amount = 1000)
            )
        ),
        Payer(
            payer = members[2],
            receivers = listOf(
                Receiver(receiver = members[1], amount = 1000),
                Receiver(receiver = members[2], amount = 1000),
                Receiver(receiver = members[3], amount = 1000),
                Receiver(receiver = members[4], amount = 1000),
                Receiver(receiver = members[5], amount = 1000),
                Receiver(receiver = members[6], amount = 1000)
            )
        ),
    )

    CalculateScreen(
        navController = rememberNavController(),
        state = CalculateViewModel.State(payoffs = payoffs)
    )
}

