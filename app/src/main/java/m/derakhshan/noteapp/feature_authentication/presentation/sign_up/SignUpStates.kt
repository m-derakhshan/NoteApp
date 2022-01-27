package m.derakhshan.noteapp.feature_authentication.presentation.sign_up

data class SignUpStates(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val isFormShown: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val waitingServerResponse: Boolean = false
)