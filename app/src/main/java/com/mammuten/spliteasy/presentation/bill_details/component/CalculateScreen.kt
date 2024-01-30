package com.mammuten.spliteasy.presentation.bill_details.component

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculateScreen(
    navController: NavController,
    userList: List<Pair<String, List<Pair<String, Double>>>>
) {
    Scaffold (
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
            )
        },
        content = { innerPadding ->
            Column (
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
                    userList.forEach{ user ->
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
                                            modifier = Modifier
                                                .weight(0.5f),
                                            text = user.first,
                                            textAlign = TextAlign.Center
                                        )
                                        Column(
                                            modifier = Modifier
                                                .weight(1f),
                                            content = {
                                                user.second.forEach { info ->
                                                    Row(
                                                        verticalAlignment =
                                                            Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(1.dp),
                                                                text = info.first,
                                                                textAlign = TextAlign.Center
                                                            )
                                                            Text(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(1.dp),
                                                                text = info.second.toString(),
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
fun CalculateDialogPreview(){
    CalculateScreen(navController = rememberNavController(),
        userList = listOf(
            Pair("Member 1", listOf(
                Pair("Member 2", 12.0),
                Pair("Member 3", 12.0),
                Pair("Member 4", 12.0),
                Pair("Member 5", 12.0),
                Pair("Member 6", 12.0),
                Pair("Member 7", 12.0)
            )),
            Pair("Member 8", listOf(
                Pair("Member 2", 12.0),
                Pair("Member 3", 12.0),
                Pair("Member 4", 12.0),
                Pair("Member 5", 12.0),
                Pair("Member 6", 12.0),
                Pair("Member 7", 12.0)
            )),
            Pair("Member 9", listOf(
                Pair("Member 2", 12.0),
                Pair("Member 3", 12.0),
                Pair("Member 4", 12.0),
                Pair("Member 5", 12.0),
                Pair("Member 6", 12.0),
                Pair("Member 7", 12.0)
            ))
        )
    )
}

