package com.ys.jetnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ys.jetnote.data.NotesDataSource
import com.ys.jetnote.model.Note
import com.ys.jetnote.screen.NoteScreen
import com.ys.jetnote.ui.theme.JetNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val notes = remember {
                        mutableStateListOf<Note>()
                    }

                    NoteScreen(
                        notes = notes,
                        onAddNote = {
                            notes.add(it)
                        },
                        onRemoveNote = {
                            notes.remove(it)
                        }
                    )
                }
            }
        }
    }
}