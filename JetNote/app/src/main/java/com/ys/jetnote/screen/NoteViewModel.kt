package com.ys.jetnote.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ys.jetnote.data.NotesDataSource
import com.ys.jetnote.model.Note

class NoteViewModel() : ViewModel() {
    private val noteList = mutableStateListOf<Note>()

    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }

    fun addNote(note: Note) {
        noteList.add(note)
    }

    fun removeNote(note: Note) {
        noteList.remove(note)
    }

    fun getAllNotes(): List<Note> {
        return noteList
    }
}