package com.localazy.quicknote.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.localazy.android.LocalazyLocale

@Composable
fun ShowLocales(
    items: List<LocalazyLocale>,
    onChange: (LocalazyLocale) -> Unit,
    onHelp: () -> Unit
) {
    Column {
        LazyColumnFor(items = items, modifier = Modifier.padding(0.dp, 8.dp)) {
            TextButton(
                onClick = {
                    onChange(it)
                },
                modifier = Modifier.padding(16.dp, 4.dp, 4.dp, 4.dp).fillMaxWidth()
            ) {
                val name = "${it.localizedName}${if (!it.isFullyTranslated) " (incomplete)" else ""}"
                Text(name)
            }
        }
        TextButton(
            onClick = {
                onHelp()
            },
            modifier = Modifier.padding(16.dp, 12.dp, 4.dp, 4.dp).fillMaxWidth()
        ) {
            Text("Help us translate the app!")
        }
    }
}