package m.derakhshan.noteapp.feature_note.presentation.notes

import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.utils.NoteOrder

sealed class NoteEvents {
    data class ChangeOrder(val order: NoteOrder) : NoteEvents()
    data class DeleteNote(val note: Note) : NoteEvents()
    object RestoreNote : NoteEvents()
    object ToggleOrderSection : NoteEvents()
    object ListScrollUp : NoteEvents()
    object ListScrollDown : NoteEvents()
}
