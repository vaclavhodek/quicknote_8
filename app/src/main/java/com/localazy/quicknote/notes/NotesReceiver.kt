package com.localazy.quicknote.notes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val NOTES_RECEIVER_ACTION = "com.localazy.quicknote.actions.UPDATE_NOTES"

class NotesReceiver(val update: () -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        update()
    }

}