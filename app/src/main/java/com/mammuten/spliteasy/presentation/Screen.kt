package com.mammuten.spliteasy.presentation

sealed class Screen(val route: String) {
    data object GroupsScreen : Screen("groups_screen")
    data object AddEditGroupScreen : Screen("add_edit_group_screen")
    data object GroupDetailsScreen : Screen("group_details_screen")
}