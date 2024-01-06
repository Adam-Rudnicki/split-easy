package com.mammuten.spliteasy.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mammuten.spliteasy.presentation.add_edit_bill.AddEditBillScreen
import com.mammuten.spliteasy.presentation.add_edit_group.AddEditGroupScreen
import com.mammuten.spliteasy.presentation.bill_details.BillDetailsScreen
import com.mammuten.spliteasy.presentation.group_details.GroupDetailsScreen
import com.mammuten.spliteasy.presentation.groups.GroupsScreen
import com.mammuten.spliteasy.presentation.ui.theme.SplitEasyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplitEasyTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    SplitEasyApp()
                }
            }
        }
    }
}

@Composable
private fun SplitEasyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.GroupsScreen.route
    ) {
        composable(route = Screen.GroupsScreen.route) {
            GroupsScreen(navController = navController)
        }
        composable(
            route = "${Screen.AddEditGroupScreen.route}?groupId={groupId}",
            arguments = listOf(
                navArgument(name = "groupId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditGroupScreen(navController = navController)
        }
        composable(
            route = "${Screen.GroupDetailsScreen.route}/{groupId}",
            arguments = listOf(
                navArgument(name = "groupId") {
                    type = NavType.IntType
                }
            )
        ) {
            GroupDetailsScreen(navController = navController)
        }
        composable(
            route = "${Screen.AddEditBillScreen.route}/{groupId}?billId={billId}",
            arguments = listOf(
                navArgument(name = "groupId") {
                    type = NavType.IntType
                },
                navArgument(name = "billId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditBillScreen(navController = navController)
        }
        composable(
            route = "${Screen.BillDetailsScreen.route}/{billId}",
            arguments = listOf(
                navArgument(name = "billId") {
                    type = NavType.IntType
                }
            )
        ) {
            BillDetailsScreen(navController = navController)
        }
    }
}