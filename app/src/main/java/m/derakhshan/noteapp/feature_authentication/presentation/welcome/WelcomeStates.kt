package m.derakhshan.noteapp.feature_authentication.presentation.welcome

import androidx.compose.ui.graphics.Color
import m.derakhshan.noteapp.ui.theme.Gray


data class WelcomeStates(
    val triangleBackgroundAngle: Float = 0f,
    val triangleBackgroundScale: Float = 1f,
    val triangleBackgroundColor: Color = Gray,
) {
    fun isWelcomeShown() = triangleBackgroundAngle == 0f
}
