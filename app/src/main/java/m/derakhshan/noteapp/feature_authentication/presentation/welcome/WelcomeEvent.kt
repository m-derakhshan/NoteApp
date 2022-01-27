package m.derakhshan.noteapp.feature_authentication.presentation.welcome

sealed class WelcomeEvent {
    object LoginClicked : WelcomeEvent()
    object SignUpClicked : WelcomeEvent()
    object BackPressed : WelcomeEvent()
}
