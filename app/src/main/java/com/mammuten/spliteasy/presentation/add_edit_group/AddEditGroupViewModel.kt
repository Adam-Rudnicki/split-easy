package com.mammuten.spliteasy.presentation.add_edit_group

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.usecase.group.GroupUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditGroupViewModel @Inject constructor(
    private val groupUseCases: GroupUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf(TextFieldState())
        private set

    var description by mutableStateOf(TextFieldState())
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentGroupId: Int? = null

    init {
        savedStateHandle.get<Int>("groupId")?.let { groupId ->
            if (groupId != -1) {
                viewModelScope.launch {
                    groupUseCases.getGroupByIdUseCase(groupId).firstOrNull()?.let { group ->
                        currentGroupId = group.id
                        name = name.copy(value = group.name)
                        group.description?.let { description = description.copy(value = it) }
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditGroupEvent) {
        when (event) {
            is AddEditGroupEvent.EnteredName -> {
                val trimmed = event.value.trim()
                val error: InvalidInputError? = when {
                    !Group.IS_NAME_REQUIRED && trimmed.isEmpty() -> null
                    trimmed.length < Group.MIN_NAME_LEN -> InvalidInputError.TooShort(Group.MIN_NAME_LEN)
                    trimmed.length > Group.MAX_NAME_LEN -> InvalidInputError.TooLong(Group.MAX_NAME_LEN)
                    else -> null
                }
                name = name.copy(
                    value = event.value,
                    error = error
                )
            }

            is AddEditGroupEvent.EnteredDescription -> {
                val trimmed = event.value.trim()
                val error: InvalidInputError? = when {
                    !Group.IS_DESC_REQUIRED && trimmed.isEmpty() -> null
                    trimmed.length < Group.MIN_DESC_LEN -> InvalidInputError.TooShort(Group.MIN_DESC_LEN)
                    trimmed.length > Group.MAX_DESC_LEN -> InvalidInputError.TooLong(Group.MAX_DESC_LEN)
                    else -> null
                }
                description = description.copy(
                    value = event.value,
                    error = error
                )
            }

            is AddEditGroupEvent.SaveGroup -> {
                viewModelScope.launch {
                    if (Group.IS_NAME_REQUIRED && name.value.isBlank()) name =
                        name.copy(error = InvalidInputError.Required)
                    if (Group.IS_DESC_REQUIRED && description.value.isBlank()) description =
                        description.copy(error = InvalidInputError.Required)
                    if (name.error != null || description.error != null) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "Please fill properly all fields"))
                        return@launch
                    }
                    groupUseCases.upsertGroupUseCase(
                        Group(
                            id = currentGroupId,
                            name = name.value,
                            description = description.value.takeIf { it.isNotBlank() }
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveGroup)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveGroup : UiEvent()
    }
}