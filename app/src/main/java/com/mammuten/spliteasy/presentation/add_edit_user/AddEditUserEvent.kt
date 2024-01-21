package com.mammuten.spliteasy.presentation.add_edit_user

sealed interface AddEditUserEvent {
    data class EnteredName(val value: String) : AddEditUserEvent
    data class EnteredSurname(val value: String) : AddEditUserEvent
    data class EnteredNick(val value: String) : AddEditUserEvent
    data class EnteredPhone(val value: String) : AddEditUserEvent
    data class EnteredDescription(val value: String) : AddEditUserEvent
    data object SaveUser : AddEditUserEvent
}