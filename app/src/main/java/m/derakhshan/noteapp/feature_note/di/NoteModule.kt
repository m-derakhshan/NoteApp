package m.derakhshan.noteapp.feature_note.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import m.derakhshan.noteapp.feature_note.data.data_source.NoteDatabase
import m.derakhshan.noteapp.feature_note.data.repository.NoteRepositoryImpl
import m.derakhshan.noteapp.feature_note.domain.repository.NoteRepository
import m.derakhshan.noteapp.feature_note.domain.use_case.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NoteModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "NoteDatabase"
        ).build()
    }


    @Provides
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(
            noteDao = database.noteDao
        )
    }

    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            deleteNoteUseCase = DeleteNoteUseCase(repository = repository),
            getNoteByIdUseCase = GetNoteByIdUseCase(repository = repository),
            insertNoteUseCase = InsertNoteUseCase(repository = repository),
            getNotesUseCase = GetNotesUseCase(repository = repository)
        )
    }

}