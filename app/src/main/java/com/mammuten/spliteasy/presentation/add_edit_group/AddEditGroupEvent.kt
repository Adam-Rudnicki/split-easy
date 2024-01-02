package com.mammuten.spliteasy.presentation.add_edit_group

sealed interface AddEditGroupEvent {
    data class EnteredName(val value: String) : AddEditGroupEvent
    data class EnteredDescription(val value: String) : AddEditGroupEvent
    data object SaveGroup : AddEditGroupEvent
}
