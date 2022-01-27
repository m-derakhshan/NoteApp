package m.derakhshan.noteapp.core.presentation.composable


sealed class MainNavGraph(val route: String) {
    object WelcomeScreen : MainNavGraph(route = "WelcomeScreen")
    object NoteScreen : MainNavGraph(route = "NoteScreen")
    object AddEditNoteScreen : MainNavGraph(route = "AddEditNoteScreen")
}
