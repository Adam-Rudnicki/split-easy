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
import com.mammuten.spliteasy.presentation.add_edit_contribution.AddEditContributionScreen
import com.mammuten.spliteasy.presentation.add_edit_contribution.AddEditContributionViewModel
import com.mammuten.spliteasy.presentation.add_edit_group.AddEditGroupScreen
import com.mammuten.spliteasy.presentation.add_edit_group.AddEditGroupViewModel
import com.mammuten.spliteasy.presentation.add_edit_member.AddEditMemberScreen
import com.mammuten.spliteasy.presentation.add_edit_member.AddEditMemberViewModel
import com.mammuten.spliteasy.presentation.bill_details.BillDetailsScreen
import com.mammuten.spliteasy.presentation.bill_details.BillDetailsViewModel
import com.mammuten.spliteasy.presentation.group_details.GroupDetailsScreen
import com.mammuten.spliteasy.presentation.group_details.GroupDetailsViewModel
import com.mammuten.spliteasy.presentation.groups.GroupsScreen
import com.mammuten.spliteasy.presentation.groups.GroupsViewModel
import com.mammuten.spliteasy.presentation.ui.theme.SplitEasyTheme
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
                    val eventFlow = viewModel.eventFlow
                    AddEditMemberScreen(
                        navController = navController,
                        nameState = nameState,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
            composable(
                route = "${Screen.AddEditContributionScreen.route}/{billId}/{memberId}",
                arguments = listOf(
                    navArgument(name = "billId") { type = NavType.IntType },
                    navArgument(name = "memberId") { type = NavType.IntType }
                ),
                content = {
                    val viewModel = hiltViewModel<AddEditContributionViewModel>()
                    val memberIdState = viewModel.memberId
                    val amountPaidState = viewModel.amountPaid
                    val amountOwedState = viewModel.amountOwed
                    val eventFlow = viewModel.eventFlow
                    AddEditContributionScreen(
                        navController = navController,
                        memberIdState = memberIdState,
                        amountPaidState = amountPaidState,
                        amountOwedState = amountOwedState,
                        onEvent = viewModel::onEvent,
                        eventFlow = eventFlow
                    )
                }
            )
        }
    )
}