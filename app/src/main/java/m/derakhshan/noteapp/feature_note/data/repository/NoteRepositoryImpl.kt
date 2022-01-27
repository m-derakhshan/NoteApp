package m.derakhshan.noteapp.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import m.derakhshan.noteapp.feature_note.data.data_source.NoteDao
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> =
        noteDao.getNotes()


    override suspend fun getNoteByID(id: Int): Note =
        noteDao.getNoteByID(id = id)


    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNote(note = note)


    override suspend fun insertNote(note: Note) =
        noteDao.insertNote(note = note)

}