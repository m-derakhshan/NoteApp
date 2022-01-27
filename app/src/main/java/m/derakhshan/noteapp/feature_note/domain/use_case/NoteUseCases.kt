package m.derakhshan.noteapp.feature_note.domain.use_case

data class NoteUseCases(
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val getNotesUseCase: GetNotesUseCase,
)
