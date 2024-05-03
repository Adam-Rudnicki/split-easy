package com.mammuten.spliteasy.presentation.add_users_to_group

import com.mammuten.spliteasy.domain.model.User

sealed interface AddUsersToGroupEvent {
    data class ToggleUserSelection(val user: User, val isChecked: Boolean) : AddUsersToGroupEvent
    data object SaveUsers : AddUsersToGroupEvent
}