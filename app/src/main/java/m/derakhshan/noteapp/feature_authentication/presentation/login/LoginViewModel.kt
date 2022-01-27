package m.derakhshan.noteapp.feature_authentication.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = mutableStateOf(LoginStates())
    val state: State<LoginStates> = _state


    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.ShowForm -> {
                _state.value = _state.value.copy(
                    isFormShown = true
                )
            }
            is LoginEvents.HideForm -> {
                _state.value = _state.value.copy(
                    isFormShown = false
                )
            }
            is LoginEvents.Login -> {
                loginServer()
            }

            is LoginEvents.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
            is LoginEvents.UsernameChanged -> {
                _state.value = _state.value.copy(
                    username = event.username
                )
            }

            is LoginEvents.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }

            is LoginEvents.ForgetPassword -> {

            }

        }
    }


    private fun loginServer() {
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