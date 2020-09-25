package com.localazy.quicknote.localazy

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.localazy.android.Localazy
import com.localazy.android.LocalazyLocale
import kotlinx.coroutines.launch

class LocaleViewModel(application: Application) : AndroidViewModel(application) {

    private val localazyListener = LocalazyWrapperListener {
        viewModelScope.launch {
            update()
        }
    }

    var locales by mutableStateOf(listOf<LocalazyLocale>())
        private set

    init {
        Localazy.setListener(localazyListener)
        update()
    }

    private fun update() {
        locales = Localazy.getLocales() ?: emptyList()
    }

}