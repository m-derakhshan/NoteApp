package m.derakhshan.noteapp.feature_authentication.presentation.welcome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import m.derakhshan.noteapp.ui.theme.Gray
import m.derakhshan.noteapp.ui.theme.VeryLightBlue
import m.derakhshan.noteapp.ui.theme.VeryLightGreen


class WelcomeViewModel : ViewModel() {

    private val _state = mutableStateOf(WelcomeStates())
    val state: State<WelcomeStates> = _state

    fun onEvent(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.LoginClicked -> {
                _state.value = _state.value.copy(
                    triangleBackgroundAngle = 50f,
                    triangleBackgroundScale = 5f,
                    triangleBackgroundColor = VeryLightBlue
                )
            }
            is WelcomeEvent.SignUpClicked -> {
                _state.value = _state.value.copy(
                    triangleBackgroundAngle = 50f,
                    triangleBackgroundScale = 5f,
                    triangleBackgroundColor = VeryLightGreen

                )
            }
            is WelcomeEvent.BackPressed -> {
                _state.value = _state.value.copy(
                    triangleBackgroundAngle = 0f,
                    triangleBackgroundScale = 1f,
                    triangleBackgroundColor = Gray
                )
            }
        }
    }


}