package m.derakhshan.noteapp.feature_note.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.derakhshan.noteapp.feature_note.domain.model.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE id =:id")
    suspend fun getNoteByID(id: Int): Note


}