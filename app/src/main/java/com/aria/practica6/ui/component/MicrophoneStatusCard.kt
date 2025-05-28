package com.aria.practica6.ui.component

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MicrophoneStatusCard(
    isRecording: Boolean,
    lastAudioUri: Uri?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isRecording) "🎙 Grabando..." else "Micrófono en espera",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            lastAudioUri?.let {
                Text(
                    text = "Último archivo: ${it.lastPathSegment}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}