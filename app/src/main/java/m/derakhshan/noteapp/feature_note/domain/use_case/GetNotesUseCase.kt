package m.derakhshan.noteapp.feature_note.domain.use_case

import androidx.compose.ui.text.toLowerCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.repository.NoteRepository
import m.derakhshan.noteapp.feature_note.domain.utils.NoteOrder
import m.derakhshan.noteapp.feature_note.domain.utils.OrderType
import java.util.*
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.OrderByDate(OrderType.Ascending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder) {
                is NoteOrder.OrderByDate -> {
                    if (noteOrder.orderType is OrderType.Ascending)
                        notes.sortedBy { it.timestamp }
                    else
                        notes.sortedByDescending { it.timestamp }
                }
                is NoteOrder.OrderByColor -> {
                    if (noteOrder.orderType is OrderType.Ascending)
                        notes.sortedBy { it.color }
                    else
                        notes.sortedByDescending { it.color }
                }
                is NoteOrder.OrderByTitle -> {
                    if (noteOrder.orderType is OrderType.Ascending)
                        notes.sortedBy { it.title.lowercase() }
                    else
                        notes.sortedByDescending { it.title.lowercase() }
                }
            }
        }
    }

}