package io.github.estivensh4.movilboxapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PopupBox(
    showPopup: Boolean,
    onClickOutside: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showPopup) {
        Popup(
            alignment = Alignment.TopEnd,
            properties = PopupProperties(
                excludeFromSystemGesture = true,
            ),
            onDismissRequest = onClickOutside
        ) {
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}