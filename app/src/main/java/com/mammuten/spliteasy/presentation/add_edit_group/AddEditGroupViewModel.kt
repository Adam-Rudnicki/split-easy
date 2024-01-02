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
        viewModelScope.launch {
            savedStateHandle.get<Int>("groupId")?.let { id ->
                if (id != -1) {
                    groupUseCases.getGroupByIdUseCase(id).firstOrNull()?.let { group ->
                        currentGroupId = group.id
                        name = name.copy(value = group.name)
                        group.description?.let { description = description.copy(value = it) }
                    }
                }
            }
            name = name.copy(
                error = InvalidInputError.checkInput(
                    text = name.value,
                    isRequired = Group.IS_NAME_REQUIRED,
                    minLength = Group.MIN_NAME_LEN,
                    maxLength = Group.MAX_NAME_LEN
                )
            )
            description = description.copy(
                error = InvalidInputError.checkInput(
                    text = description.value,
                    isRequired = Group.IS_DESC_REQUIRED,
                    minLength = Group.MIN_DESC_LEN,
                    maxLength = Group.MAX_DESC_LEN
                )
            )
        }
    }

    fun onEvent(event: AddEditGroupEvent) {
        when (event) {
            is AddEditGroupEvent.EnteredName -> {
                val error: InvalidInputError? = InvalidInputError.checkInput(
                    text = event.value,
                    isRequired = Group.IS_NAME_REQUIRED,
                    minLength = Group.MIN_NAME_LEN,
                    maxLength = Group.MAX_NAME_LEN
                )
                name = name.copy(
                    value = event.value,
                    error = error
                )
            }

            is AddEditGroupEvent.EnteredDescription -> {
                val error: InvalidInputError? = InvalidInputError.checkInput(
                    text = event.value,
                    isRequired = Group.IS_DESC_REQUIRED,
                    minLength = Group.MIN_DESC_LEN,
                    maxLength = Group.MAX_DESC_LEN
                )
                description = description.copy(
                    value = event.value,
                    error = error
                )
            }

            is AddEditGroupEvent.SaveGroup -> {
                viewModelScope.launch {
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