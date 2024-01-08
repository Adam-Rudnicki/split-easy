package com.mammuten.spliteasy.presentation.add_edit_member

sealed interface AddEditMemberEvent {
    data class EnteredName(val value: String) : AddEditMemberEvent
    data object SaveMember : AddEditMemberEvent
}