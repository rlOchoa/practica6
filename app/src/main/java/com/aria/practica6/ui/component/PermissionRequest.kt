package com.aria.practica6.ui.component

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequest(
    onPermissionsGranted: () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    LaunchedEffect(Unit) {
        permissionsState.launchMultiplePermissionRequest()
    }

    when {
        permissionsState.allPermissionsGranted -> {
            onPermissionsGranted()
        }

        permissionsState.shouldShowRationale -> {
            RationaleMessage(onRequestAgain = {
                permissionsState.launchMultiplePermissionRequest()
            })
        }

        else -> {
            // Esperando permisos o permanentemente denegados
            RequestDeniedMessage()
        }
    }
}

@Composable
fun RationaleMessage(onRequestAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Esta aplicación necesita permisos de cámara y micrófono para funcionar correctamente.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestAgain) {
            Text("Volver a solicitar permisos")
        }
    }
}

@Composable
fun RequestDeniedMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Los permisos fueron denegados permanentemente.\nActívalos desde la configuración del sistema.")
    }
}