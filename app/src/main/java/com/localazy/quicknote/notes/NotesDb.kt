package com.localazy.quicknote.notes

import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.localazy.quicknote.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotesDb(val context: Context) {

    val db = Room.databaseBuilder(
        if (context.applicationContext == null) context else context.applicationContext,
        AppDatabase::class.java,
        "db-notes"
    ).enableMultiInstanceInvalidation().build()


    fun insert(note: String, sendBroadcast: Boolean = false) {
        if (note.isEmpty()) return
        val noteObj = Note((System.currentTimeMillis() % Int.MAX_VALUE).toInt(), note)
        insert(noteObj, sendBroadcast)
    }


    fun insert(note: Note, sendBroadcast: Boolean = false) {
        GlobalScope.launch {
            db.notes().insert(note)
            if (sendBroadcast) {
                update()
            }
        }
    }


    fun remove(note: Note, sendBroadcast: Boolean = false) {
        GlobalScope.launch {
            db.notes().delete(note)
            if (sendBroadcast) {
                update()
            }
        }
    }


    fun list(setter: (List<Note>) -> Unit) {
        GlobalScope.launch {
            setter(db.notes().getAll())
        }
    }


    fun update() {
        context.sendBroadcast(Intent(NOTES_RECEIVER_ACTION))
    }

}