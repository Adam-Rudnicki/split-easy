package com.mammuten.spliteasy.presentation.groups

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mammuten.spliteasy.presentation.groups.components.OrderSection
import com.mammuten.spliteasy.presentation.Screen

@Composable
fun GroupsScreen(
    navController: NavController,
    viewModel: GroupsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditGroupScreen.route) },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add group")
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
            ) {
                OrderSection(
                    modifier = Modifier.fillMaxWidth(),
                    groupOrder = state.groupOrder,
                    onOrderChange = { viewModel.onEvent(GroupsEvent.Order(it)) }
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    color = Color.Black
                )
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = state.groups,
                        key = { group -> group.id!! }
                    ) { group ->
                        OutlinedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            border = BorderStroke(width = 1.dp, color = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    navController.navigate(
                                        Screen.GroupDetailsScreen.route + "/${group.id}"
                                    )
                                }
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = group.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = group.created.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                group.description?.let {
                                    Text(
                                        text = it,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}