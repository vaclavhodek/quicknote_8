package com.localazy.quicknote.notes

import android.app.Application
import android.content.IntentFilter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val db = NotesDb(application.applicationContext)

    var notes by mutableStateOf(listOf<Note>())
        private set

    private val updateReceiver = NotesReceiver { loadItemsFromDb() }

    // Load initial data from Room asynchronously.
    init {
        context.registerReceiver(updateReceiver, IntentFilter(NOTES_RECEIVER_ACTION))
        loadItemsFromDb()
    }

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(updateReceiver)
    }

    private fun loadItemsFromDb() {
        db.list {
            viewModelScope.launch { notes = it }
        }
    }

    fun addNote(note: String) {
        // Generate ID in a simple way - from timestamp.
        val noteObj = Note((System.currentTimeMillis() % Int.MAX_VALUE).toInt(), note)
        db.insert(noteObj)
        notes = notes + listOf(noteObj)
    }

    fun removeNote(note: Note) {
        notes = notes - listOf(note)
        db.remove(note)
    }

}