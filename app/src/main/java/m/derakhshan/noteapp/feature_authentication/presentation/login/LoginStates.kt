package m.derakhshan.noteapp.feature_authentication.presentation.login

data class LoginStates(
    val username: String = "",
    val password: String = "",
    val isFormShown: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val waitingServerResponse: Boolean = false
)