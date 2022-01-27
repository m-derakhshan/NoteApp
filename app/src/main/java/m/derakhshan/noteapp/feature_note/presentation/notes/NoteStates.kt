package m.derakhshan.noteapp.feature_note.presentation.notes

import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.utils.NoteOrder
import m.derakhshan.noteapp.feature_note.domain.utils.OrderType

data class NoteStates(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.OrderByDate(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isAddNoteVisible: Boolean = false
)
