package com.ys.jetnote.data

import com.ys.jetnote.model.Note

class NotesDataSource {
    fun loadNotes(): List<Note> {
        val notes = mutableListOf<Note>()

        repeat(10) {
            notes.add(
                Note(title = "Title $it", description = "Description $it")
            )
        }

        return notes
    }
}