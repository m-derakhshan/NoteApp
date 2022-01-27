package m.derakhshan.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import m.derakhshan.noteapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao:NoteDao
}