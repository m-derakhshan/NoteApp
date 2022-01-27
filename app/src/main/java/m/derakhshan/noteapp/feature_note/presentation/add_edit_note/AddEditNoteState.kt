package m.derakhshan.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import m.derakhshan.noteapp.feature_note.domain.model.Note

data class AddEditNoteState(
    val title: NoteTextFieldState = NoteTextFieldState(hint = "Enter title..."),
    val content: NoteTextFieldState = NoteTextFieldState(hint = "Enter content..."),
    val color: Int = Note.colors.random().toArgb()
)


