package m.derakhshan.noteapp.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import m.derakhshan.noteapp.core.data.data_source.Setting
import m.derakhshan.noteapp.feature_note.domain.model.InvalidNoteException
import m.derakhshan.noteapp.feature_note.domain.model.Note
import m.derakhshan.noteapp.feature_note.domain.use_case.NoteUseCases
import javax.inject.Inject


@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val useCases: NoteUseCases,
    private val setting: Setting,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditNoteState())
    val state: State<AddEditNoteState> = _state

    private val _snackBar = MutableSharedFlow<SnackBarEvent>()
    val snackBar = _snackBar.asSharedFlow()

    private var currentNoteID: Int? = null


    init {
        val noteId = savedStateHandle.get<Int>("noteId")
        if (noteId != -1 && noteId != null) {
            currentNoteID = noteId
            viewModelScope.launch {
                val note = useCases.getNoteByIdUseCase(noteId)
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        text = note.title,
                        isHintVisible = false
                    ),
                    content = _state.value.content.copy(
                        text = note.content,
                        isHintVisible = false
                    ),
                    color = note.color
                )
                Log.i("Log", "addEditNoteViewModel: color:${note.color}")
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeContent -> {
                _state.value = _state.value.copy(
                    content = _state.value.content.copy(
                        text = event.content
                    )
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _state.value = _state.value.copy(
                    content = _state.value.content.copy(
                        isHintVisible = !event.focusState.isFocused && _state.value.content.text.isBlank()
                    )
                )
            }
            is AddEditNoteEvent.ChangeTitle -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        text = event.title
                    )
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _state.value = _state.value.copy(
                    title = _state.value.title.copy(
                        isHintVisible = !event.focusState.isFocused && _state.value.title.text.isBlank()
                    )
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _state.value = _state.value.copy(
                    color = event.color
                )
            }
            is AddEditNoteEvent.Save -> {
                viewModelScope.launch {
                    try {
                        useCases.insertNoteUseCase(
                            Note(
                                id = currentNoteID ?: setting.lastNoteId,
                                title = _state.value.title.text,
                                content = _state.value.content.text,
                                color = _state.value.color,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        _snackBar.emit(
                            SnackBarEvent.ShowSnackBar(
                                message = "Note successfully inserted!"
                            )
                        )
                    } catch (e: InvalidNoteException) {
                        _snackBar.emit(
                            SnackBarEvent.ShowSnackBar(
                                message = e.message ?: "Can't insert note."
                            )
                        )
                    }
                }
            }
        }
    }

}