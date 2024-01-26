package com.mammuten.spliteasy.presentation.add_edit_member

import com.mammuten.spliteasy.domain.model.User

sealed interface AddEditMemberEvent {
    data class EnteredName(val value: String) : AddEditMemberEvent
    data object SaveMember : AddEditMemberEvent
    data class ToggleUserSelection(val user: User, val isChecked: Boolean) : AddEditMemberEvent
}