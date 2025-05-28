package com.aria.practica6.ui.screen

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aria.practica6.viewmodel.MicrophoneViewModel
import com.aria.practica6.viewmodel.SensorViewModel
import com.aria.practica6.audio.AudioRecorderManager
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MicrophoneScreen() {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val viewModel = remember {
        MicrophoneViewModel(
            recorderManager = AudioRecorderManager(context.applicationContext),
            sensorViewModel = SensorViewModel(context.applicationContext as android.app.Application)
        )
    }

    val isRecording by viewModel.isRecording.collectAsState()
    val lastAudioUri by viewModel.lastAudioUri.collectAsState()

    val audioPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    // Solicita el permiso al cargar
    LaunchedEffect(Unit) {
        if (!audioPermission.status.isGranted) {
            audioPermission.launchPermissionRequest()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Grabación de Audio") }
            )
        },
        content = { innerPadding ->
            if (audioPermission.status.isGranted) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!isRecording) {
                            Button(onClick = {
                                viewModel.startRecording(
                                    onStart = { uri ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = "Grabando en: ${uri.lastPathSegment ?: "desconocido"}")
                                        }
                                    },
                                    onError = { error ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = error)
                                        }
                                    }
                                )
                            }) {
                                Text("Iniciar Grabación")
                            }
                        } else {
                            Button(onClick = {
                                viewModel.stopRecording(
                                    onStop = { uri ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = "Audio guardado en: ${uri?.lastPathSegment ?: "desconocido"}")
                                        }
                                    },
                                    onError = { error ->
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = error)
                                        }
                                    }
                                )
                            }) {
                                Text("Detener Grabación")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (lastAudioUri != null) {
                            Text("Último archivo: ${lastAudioUri?.lastPathSegment}")
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Permiso de micrófono requerido.")
                }
            }
        }
    )
}