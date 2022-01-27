package m.derakhshan.noteapp.feature_note.domain.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.noteapp.feature_note.domain.model.Note

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteByID(id: Int): Note

    suspend fun deleteNote(note: Note)

    suspend fun insertNote(note: Note)
}
