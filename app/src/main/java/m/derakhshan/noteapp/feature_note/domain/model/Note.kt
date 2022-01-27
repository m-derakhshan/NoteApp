package m.derakhshan.noteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import m.derakhshan.noteapp.ui.theme.*


@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey
    val id: Int?,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long
) {
    companion object{
        val colors = listOf(
            LightGreen, LightBlue, LightPurple,
            LightOrange, LightRed, LightYellow
        )
    }
}


class InvalidNoteException(message: String) : Exception(message)
