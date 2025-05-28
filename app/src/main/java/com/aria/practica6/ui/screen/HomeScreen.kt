package com.aria.practica6.ui.screen

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aria.practica6.ui.component.RationaleMessage
import com.aria.practica6.ui.component.RequestDeniedMessage
import com.aria.practica6.ui.component.SensorCard
import com.aria.practica6.viewmodel.SensorViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onNavigateToMicrophone: () -> Unit
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            permissionsState.allPermissionsGranted -> {
                HomeScreenContent(
                    onNavigateToSettings = onNavigateToSettings,
                    onNavigateToCamera = onNavigateToCamera,
                    onNavigateToMicrophone = onNavigateToMicrophone
                )
            }

            permissionsState.shouldShowRationale -> {
                RationaleMessage {
                    permissionsState.launchMultiplePermissionRequest()
                }
            }

            else -> {
                RequestDeniedMessage()
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    onNavigateToSettings: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onNavigateToMicrophone: () -> Unit,
    sensorViewModel: SensorViewModel = viewModel()
) {
    val sensors by sensorViewModel.sensorStates.collectAsState()
    val proximity = sensors["Proximidad"]?.value ?: -1f
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Mostrar Snackbar cuando se detecta algo muy cercano
    LaunchedEffect(proximity) {
        if (proximity in 0.0..1.0) {
            snackbarHostState.showSnackbar("Objeto muy cerca del dispositivo")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Bienvenida, Aria 🌟",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Sensores activos:",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sensors.values.toList()) { sensorData ->
                    SensorCard(sensorData)
                }
            }

            Button(onClick = onNavigateToCamera, modifier = Modifier.fillMaxWidth()) {
                Text("Ir a Cámara")
            }

            Button(onClick = onNavigateToMicrophone, modifier = Modifier.fillMaxWidth()) {
                Text("Ir a Micrófono")
            }

            Button(onClick = onNavigateToSettings, modifier = Modifier.fillMaxWidth()) {
                Text("Configuración de Tema")
            }
        }
    }
}