package com.mammuten.spliteasy.presentation

sealed class Screen(val route: String) {
    data object GroupsScreen : Screen("groups_screen")
    data object AddEditGroupScreen : Screen("add_edit_group_screen")
    data object GroupDetailsScreen : Screen("group_details_screen")
    data object AddEditBillScreen : Screen("add_edit_bill_screen")
    data object BillDetailsScreen : Screen("bill_details_screen")
    data object AddEditMemberScreen : Screen("add_edit_member_screen")
    data object AddEditContributionScreen : Screen("add_edit_contribution_screen")
}