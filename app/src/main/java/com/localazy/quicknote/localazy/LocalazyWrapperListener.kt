package com.localazy.quicknote.localazy

import com.localazy.android.LocalazyId
import com.localazy.android.LocalazyListener
import java.util.*

class LocalazyWrapperListener(val body: () -> Unit) : LocalazyListener {

    override fun missingTextFound(p0: LocalazyId?, p1: Locale?, p2: String?) {}

    override fun missingKeyFound(p0: Locale?, p1: String?) {}

    override fun stringsUpdateStarted() {}

    override fun stringsUpdateFinished() {}

    override fun stringsUpdateFailed(p0: Int) {}

    override fun stringsUpdateNotNecessary() {}

    override fun stringsLoaded(fromUpdate: Boolean, success: Boolean) {
        if (success) {
            body()
        }
    }

}