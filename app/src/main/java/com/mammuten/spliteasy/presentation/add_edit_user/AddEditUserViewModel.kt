package com.mammuten.spliteasy.presentation.add_edit_user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.usecase.user.UserUseCases
import com.mammuten.spliteasy.presentation.components.InvalidInputError
import com.mammuten.spliteasy.presentation.components.input_state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditUserViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var name by mutableStateOf(TextFieldState())
        private set

    var surname by mutableStateOf(TextFieldState())
        private set

    var nick by mutableStateOf(TextFieldState())
        private set

    var isSaving by mutableStateOf(false)
        private set

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentUserId: Int? = null

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("userId")?.let { id ->
                if (id != -1) {
                    userUseCases.getUserByIdUseCase(id).firstOrNull()?.let { user ->
                        currentUserId = user.id
                        name = name.copy(value = user.name)
                        user.surname?.let { surname = surname.copy(value = it) }
                        user.nick?.let { nick = nick.copy(value = it) }
                    }
                }
            }
            name = name.copy(
                error = InvalidInputError.checkText(
                    text = name.value,
                    isRequired = User.IS_NAME_REQUIRED,
                    minLength = User.MIN_NAME_LEN,
                    maxLength = User.MAX_NAME_LEN
                )
            )
            surname = surname.copy(
                error = InvalidInputError.checkText(
                    text = surname.value,
                    isRequired = User.IS_SURNAME_REQUIRED,
                    minLength = User.MIN_SURNAME_LEN,
                    maxLength = User.MAX_SURNAME_LEN
                )
            )
            nick = nick.copy(
                error = InvalidInputError.checkText(
                    text = nick.value,
                    isRequired = User.IS_NICK_REQUIRED,
                    minLength = User.MIN_NICK_LEN,
                    maxLength = User.MAX_NICK_LEN
                )
            )
        }
    }

    fun onEvent(event: AddEditUserEvent) {
        when (event) {
            is AddEditUserEvent.EnteredName -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = User.IS_NAME_REQUIRED,
                    minLength = User.MIN_NAME_LEN,
                    maxLength = User.MAX_NAME_LEN
                )
                name = name.copy(value = event.value, error = error)
            }

            is AddEditUserEvent.EnteredSurname -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = User.IS_SURNAME_REQUIRED,
                    minLength = User.MIN_SURNAME_LEN,
                    maxLength = User.MAX_SURNAME_LEN
                )
                surname = surname.copy(value = event.value, error = error)
            }

            is AddEditUserEvent.EnteredNick -> {
                val error: InvalidInputError? = InvalidInputError.checkText(
                    text = event.value,
                    isRequired = User.IS_NICK_REQUIRED,
                    minLength = User.MIN_NICK_LEN,
                    maxLength = User.MAX_NICK_LEN
                )
                nick = nick.copy(value = event.value, error = error)
            }

            is AddEditUserEvent.SaveUser -> {
                viewModelScope.launch {
                    if (name.error != null || surname.error != null || nick.error != null) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "Please fill properly all fields"))
                        return@launch
                    }
                    isSaving = true
                    userUseCases.upsertUserUseCase(
                        User(
                            id = currentUserId,
                            name = name.value.trim(),
                            surname = surname.value.takeIf { it.isNotBlank() }?.trim(),
                            nick = nick.value.takeIf { it.isNotBlank() }?.trim()
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveUser)
                }
            }
        }
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
        data object SaveUser : UiEvent
    }
}