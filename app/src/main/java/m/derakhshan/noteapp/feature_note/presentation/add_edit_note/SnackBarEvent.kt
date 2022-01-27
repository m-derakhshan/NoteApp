package m.derakhshan.noteapp.feature_note.presentation.add_edit_note

sealed class SnackBarEvent {
    data class ShowSnackBar(val message: String) : SnackBarEvent()
}
