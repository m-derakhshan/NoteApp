package m.derakhshan.noteapp.feature_note.presentation.notes


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.use_case.NoteUseCases
import m.derakhshan.noteapp.feature_note.domain.utils.NoteOrder
import m.derakhshan.noteapp.feature_note.domain.utils.OrderType
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCase: NoteUseCases
) : ViewModel() {

    private var deletedNote: Note? = null
    private var job: Job? = null

    private val _state = mutableStateOf(NoteStates())
    val state: State<NoteStates> = _state


    init {
        getNotes(NoteOrder.OrderByDate(OrderType.Descending))
    }


    fun onEvent(event: NoteEvents) {
        when (event) {
            is NoteEvents.ChangeOrder -> {
                if (event.order != _state.value.noteOrder)
                    getNotes(event.order)
            }
            is NoteEvents.DeleteNote -> {
                viewModelScope.launch {
                    deletedNote = event.note
                    useCase.deleteNoteUseCase(event.note)
                }
            }
            is NoteEvents.RestoreNote -> {
                viewModelScope.launch {
                    deletedNote?.let { note ->
                        useCase.insertNoteUseCase(note = note)
                    }
                    deletedNote = null
                }
            }
            is NoteEvents.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
            is NoteEvents.ListScrollUp -> {
                _state.value = _state.value.copy(
                    isAddNoteVisible = true
                )
            }
            is NoteEvents.ListScrollDown -> {
                _state.value = _state.value.copy(
                    isAddNoteVisible = false
                )
            }
        }
    }


    private fun getNotes(order: NoteOrder) {
        job?.cancel()
        job = useCase.getNotesUseCase(noteOrder = order).onEach { notes ->
            _state.value = _state.value.copy(
                notes = notes,
                noteOrder = order
            )
        }.launchIn(viewModelScope)
    }
}