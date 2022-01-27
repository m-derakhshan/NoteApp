package m.derakhshan.noteapp.feature_authentication.presentation.login

sealed class LoginEvents {
    object Login : LoginEvents()
    object ShowForm : LoginEvents()
    object HideForm : LoginEvents()
    object TogglePasswordVisibility : LoginEvents()
    object ForgetPassword : LoginEvents()
    data class UsernameChanged(val username: String) : LoginEvents()
    data class PasswordChanged(val password: String) : LoginEvents()
}