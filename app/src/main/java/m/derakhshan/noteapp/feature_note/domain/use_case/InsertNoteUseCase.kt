package m.derakhshan.noteapp.feature_note.domain.use_case

import m.derakhshan.noteapp.feature_note.domain.model.InvalidNoteException
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    @Throws
    suspend operator fun invoke(note: Note) {
        when {
            note.title.isBlank() -> throw InvalidNoteException("Title can't left blank!")
            note.content.isBlank() -> throw InvalidNoteException("Content can't left blank!")
            else -> repository.insertNote(note = note)
        }
    }


}