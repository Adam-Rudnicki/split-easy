package com.mammuten.spliteasy.presentation.groups

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.order.GroupOrder
import com.mammuten.spliteasy.presentation.util.Screen
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    navController: NavController,
    state: GroupsState,
    onEvent: (GroupsEvent) -> Unit
) {
    var isContextMenuVisible by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Groups") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    // Sorting icon
                    IconButton(onClick = { isContextMenuVisible = !isContextMenuVisible }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                        DropdownMenu(
                            expanded = isContextMenuVisible,
                            onDismissRequest = { isContextMenuVisible = false },
                        ) {
                            DropdownMenuItem(
                                onClick = { onEvent(GroupsEvent.Order(GroupOrder.NameAscending))
                                    isContextMenuVisible = false },
                                text = { Text(text = "Name asc")}
                            )

                            DropdownMenuItem(
                                onClick = { onEvent(GroupsEvent.Order(GroupOrder.NameDescending))
                                    isContextMenuVisible = false },
                                text = { Text(text = "Name desc")})

                            DropdownMenuItem(
                                onClick = { onEvent(GroupsEvent.Order(GroupOrder.DateAscending))
                                    isContextMenuVisible = false },
                                text = { Text(text = "Date asc")})

                            DropdownMenuItem(
                                onClick = { onEvent(GroupsEvent.Order(GroupOrder.DateDescending))
                                    isContextMenuVisible = false },
                                text = { Text(text = "Date asc")})
                        }
                    }

                    // Show users icon
                    IconButton(onClick = { navController.navigate(Screen.UsersScreen.route) }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Show Users")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditGroupScreen.route) },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add group"
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            items(
                                items = state.groups,
                                key = { group -> group.id!! },
                                itemContent = { group ->
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
                                            },
                                        content = {
                                            Column(
                                                modifier = Modifier.padding(8.dp),
                                                content = {
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
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview
@Composable
fun GroupsScreenPreview() {
    GroupsScreen(
        navController = rememberNavController(),
        state = GroupsState(
            groups = listOf(
                Group(
                    id = 1,
                    name = "Group 1",
                    description = "Description 1",
                    created = Date()
                ),
                Group(
                    id = 2,
                    name = "Group 2",
                    description = "Description 2",
                    created = Date()
                )
            )
        ),
        onEvent = {}
    )
}