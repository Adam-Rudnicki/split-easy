package com.mammuten.spliteasy.presentation.group_details

sealed interface GroupDetailsEvent {
    data object DeleteGroup : GroupDetailsEvent
}
