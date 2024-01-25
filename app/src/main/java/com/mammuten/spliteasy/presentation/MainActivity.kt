package com.mammuten.spliteasy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mammuten.spliteasy.presentation.add_edit_bill.AddEditBillScreen
import com.mammuten.spliteasy.presentation.add_edit_bill.AddEditBillViewModel
import com.mammuten.spliteasy.presentation.manage_contributions.ManageContributionsScreen
import com.mammuten.spliteasy.presentation.manage_contributions.ManageContributionsViewModel
import com.mammuten.spliteasy.presentation.add_edit_group.AddEditGroupScreen
import com.mammuten.spliteasy.presentation.add_edit_group.AddEditGroupViewModel
import com.mammuten.spliteasy.presentation.add_edit_member.AddEditMemberScreen
import com.mammuten.spliteasy.presentation.add_users_to_group.AddEditMemberViewModel
import com.mammuten.spliteasy.presentation.add_edit_user.AddEditUserScreen
import com.mammuten.spliteasy.presentation.add_edit_user.AddEditUserViewModel
import com.mammuten.spliteasy.presentation.bill_details.BillDetailsScreen
import com.mammuten.spliteasy.presentation.bill_details.BillDetailsViewModel
import com.mammuten.spliteasy.presentation.group_details.GroupDetailsScreen
import com.mammuten.spliteasy.presentation.group_details.GroupDetailsViewModel
import com.mammuten.spliteasy.presentation.groups.GroupsScreen
import com.mammuten.spliteasy.presentation.groups.GroupsViewModel
import com.mammuten.spliteasy.presentation.ui.theme.SplitEasyTheme
import com.mammuten.spliteasy.presentation.users.UsersScreen
import com.mammuten.spliteasy.presentation.users.UsersViewModel
import com.mammuten.spliteasy.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(
            content = {
                SplitEasyTheme(
                    content = {
                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            content = { SplitEasyApp() }
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun SplitEasyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.GroupsScreen.route,
        builder = {
            composable(
                route = Screen.GroupsScreen.route,
                content = {
                    val viewModel = hiltViewModel<GroupsViewModel>()
                    val state = viewModel.state
                    GroupsScreen(
                        navController = navController,
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            )
            composable(
                route = "${Screen.AddEditGroupScreen.route}?groupId={groupId}",
                arguments = listOf(
                    navArgument(name = "groupId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
                content = {
                    val viewModel = hiltViewModel<AddEditGroupViewModel>()
                    val nameState = viewModel.name
                    val descriptionState = viewModel.description
                    val eventFlow = viewModel.eventFlow
                    AddEditGroupScreen(
                        navController = navController,
                        nameState = nameState,
                        descriptionState = descriptionState,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.GroupDetailsScreen.route}/{groupId}",
                arguments = listOf(navArgument(name = "groupId") { type = NavType.IntType }),
                content = {
                    val viewModel = hiltViewModel<GroupDetailsViewModel>()
                    val state = viewModel.state
                    val eventFlow = viewModel.eventFlow
                    GroupDetailsScreen(
                        navController = navController,
                        state = state,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.AddEditBillScreen.route}/{groupId}?billId={billId}",
                arguments = listOf(
                    navArgument(name = "groupId") { type = NavType.IntType },
                    navArgument(name = "billId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
                content = {
                    val viewModel = hiltViewModel<AddEditBillViewModel>()
                    val nameState = viewModel.name
                    val descriptionState = viewModel.description
                    val amountState = viewModel.amount
                    val dateState = viewModel.date
                    val eventFlow = viewModel.eventFlow
                    AddEditBillScreen(
                        navController = navController,
                        nameState = nameState,
                        descriptionState = descriptionState,
                        amountState = amountState,
                        dateState = dateState,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.BillDetailsScreen.route}/{billId}",
                arguments = listOf(navArgument(name = "billId") { type = NavType.IntType }),
                content = {
                    val viewModel = hiltViewModel<BillDetailsViewModel>()
                    val state = viewModel.state
                    val eventFlow = viewModel.eventFlow
                    BillDetailsScreen(
                        navController = navController,
                        state = state,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.AddEditMemberScreen.route}/{groupId}?memberId={memberId}",
                arguments = listOf(
                    navArgument(name = "groupId") { type = NavType.IntType },
                    navArgument(name = "memberId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
                content = {
                    val viewModel = hiltViewModel<AddEditMemberViewModel>()
                    val nameState = viewModel.name
                    val selectedUserState = viewModel.selectedUser
                    val users = viewModel.users
                    val eventFlow = viewModel.eventFlow
                    AddEditMemberScreen(
                        navController = navController,
                        nameState = nameState,
                        selectedUserState = selectedUserState,
                        users = users,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.ManageContributionsScreen.route}/{groupId}/{billId}",
                arguments = listOf(
                    navArgument(name = "groupId") { type = NavType.IntType },
                    navArgument(name = "billId") { type = NavType.IntType }
                ),
                content = {
                    val viewModel = hiltViewModel<ManageContributionsViewModel>()
                    val state = viewModel.state
                    val eventFlow = viewModel.eventFlow
                    ManageContributionsScreen(
                        navController = navController,
                        state = state,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = Screen.UsersScreen.route,
                content = {
                    val viewModel = hiltViewModel<UsersViewModel>()
                    val state = viewModel.state
                    val eventFlow = viewModel.eventFlow
                    UsersScreen(
                        navController = navController,
                        state = state,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.AddEditUserScreen.route}?userId={userId}",
                arguments = listOf(
                    navArgument(name = "userId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                ),
                content = {
                    val viewModel = hiltViewModel<AddEditUserViewModel>()
                    val nameState = viewModel.name
                    val surnameState = viewModel.surname
                    val nickState = viewModel.nick
                    val phoneState = viewModel.phone
                    val descriptionState = viewModel.description
                    val eventFlow = viewModel.eventFlow
                    AddEditUserScreen(
                        navController = navController,
                        nameState = nameState,
                        surnameState = surnameState,
                        nickState = nickState,
                        phoneState = phoneState,
                        descriptionState = descriptionState,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
        }
    )
}