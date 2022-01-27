package m.derakhshan.noteapp.feature_authentication.presentation.sign_up

sealed class SignUpEvents {
    object SingUp : SignUpEvents()
    object ShowForm : SignUpEvents()
    object HideForm : SignUpEvents()
    object TogglePasswordVisibility : SignUpEvents()
    data class ChangeUsername(val username: String) : SignUpEvents()
    data class ChangePhoneNumber(val phoneNumber: String) : SignUpEvents()
    data class ChangeEmail(val email: String) : SignUpEvents()
    data class ChangePassword(val password: String) : SignUpEvents()
    data class ChangeConfirmPassword(val confirmPassword: String) : SignUpEvents()
}