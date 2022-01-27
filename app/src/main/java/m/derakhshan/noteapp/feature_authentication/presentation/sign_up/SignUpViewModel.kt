package m.derakhshan.noteapp.feature_authentication.presentation.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    private val _state = mutableStateOf(SignUpStates())
    val state: State<SignUpStates> = _state


    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.SingUp -> {
                signUpServer()
            }
            is SignUpEvents.ShowForm -> {
                _state.value = _state.value.copy(
                    isFormShown = true
                )
            }
            is SignUpEvents.HideForm -> {
                _state.value = _state.value.copy(
                    isFormShown = false
                )
            }
            is SignUpEvents.ChangeUsername -> {
                _state.value = _state.value.copy(
                    username = event.username
                )
            }

            is SignUpEvents.ChangePassword -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }

            is SignUpEvents.ChangeConfirmPassword -> {
                _state.value = _state.value.copy(
                    confirmPassword = event.confirmPassword
                )
            }

            is SignUpEvents.ChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.email
                )
            }

            is SignUpEvents.ChangePhoneNumber -> {
                _state.value = _state.value.copy(
                    phoneNumber = event.phoneNumber
                )
            }

            is SignUpEvents.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
        }
    }


    private fun signUpServer(){
        _state.value = _state.value.copy(
            waitingServerResponse = true
        )
        viewModelScope.launch {
            delay(5000)
            _state.value = _state.value.copy(
                waitingServerResponse = false
            )
        }

    }
}