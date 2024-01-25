package com.mammuten.spliteasy.presentation.add_users_to_group

sealed interface AddUsersToGroupEvent {
    data object SaveUsers : AddUsersToGroupEvent
}