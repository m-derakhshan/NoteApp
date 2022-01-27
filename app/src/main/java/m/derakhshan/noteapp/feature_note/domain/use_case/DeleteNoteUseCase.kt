package m.derakhshan.noteapp.feature_note.domain.use_case

import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.repository.NoteRepository
import javax.inject.Inject


class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository)
{
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note = note)
    }
}