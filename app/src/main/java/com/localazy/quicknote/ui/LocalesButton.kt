package com.localazy.quicknote.ui

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocaleButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = {
            onClick()
        },
        modifier = Modifier.padding(16.dp, 16.dp, 8.dp, 4.dp).fillMaxWidth()
    ) {
        Row {
            Icon(asset = Icons.Filled.Language, modifier = Modifier.gravity(Alignment.CenterVertically))
            Text(text, modifier = Modifier.padding(4.dp).gravity(Alignment.CenterVertically))
        }
    }
}